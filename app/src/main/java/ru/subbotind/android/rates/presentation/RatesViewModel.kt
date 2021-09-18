package ru.subbotind.android.rates.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.subbotind.android.rates.data.network.exception.ServerErrorException
import ru.subbotind.android.rates.data.network.exception.UnexpectedServerErrorException
import ru.subbotind.android.rates.domain.entity.Rate
import ru.subbotind.android.rates.domain.repository.RateRepository
import ru.subbotind.android.rates.livedata.SingleLiveEvent
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class RatesViewModel @Inject constructor(
    private val ratesRepository: RateRepository
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable: Throwable ->
        _errorState.value = when (throwable) {
            is ServerErrorException -> ErrorState.ServerError(throwable.reason)

            is UnexpectedServerErrorException -> ErrorState.UnexpectedError(throwable.message ?: "")

            is IOException -> ErrorState.NoInternetError
            else -> ErrorState.UnexpectedError(throwable.message ?: "")
        }
    }

    private val _rates: MutableLiveData<List<Rate>> = MutableLiveData()
    val rates: LiveData<List<Rate>> = _rates

    private val _errorState: SingleLiveEvent<ErrorState> = SingleLiveEvent()
    val errorState: LiveData<ErrorState> = _errorState

    init {
        fetchRates()
    }

    fun fetchRates() {
        viewModelScope.launch(exceptionHandler) {
            _rates.value = ratesRepository.getDailyRates()
        }
    }
}

sealed class ErrorState {
    class ServerError(val cause: String) : ErrorState()
    class UnexpectedError(val cause: String) : ErrorState()
    object NoInternetError : ErrorState()
}
