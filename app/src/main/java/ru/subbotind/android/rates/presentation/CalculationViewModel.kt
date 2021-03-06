package ru.subbotind.android.rates.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.subbotind.android.rates.domain.usecase.CalculateTotalAmountUseCase
import ru.subbotind.android.rates.ui.calculation.NOMINAL_PARAM
import ru.subbotind.android.rates.ui.calculation.RATE_PARAM
import javax.inject.Inject

@HiltViewModel
class CalculationViewModel @Inject constructor(
    private val calculateTotalAmountUseCase: CalculateTotalAmountUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var rate: Double =
        savedStateHandle.get(RATE_PARAM) ?: throw IllegalArgumentException(
            "Rate should not be null!"
        )

    private var nominal: Int =
        savedStateHandle.get(NOMINAL_PARAM) ?: throw IllegalArgumentException(
            "Nominal should not be null!"
        )

    private var _total: MutableLiveData<String> = MutableLiveData()
    val total: LiveData<String> = _total

    init {
        calculate(0)
    }

    fun calculate(count: Int) {
        val totalAmount = calculateTotalAmountUseCase(count, rate, nominal)
        _total.value = String.format("%.2f", totalAmount)
    }
}
