package ru.subbotind.android.rates.data

import kotlinx.coroutines.flow.Flow
import ru.subbotind.android.rates.data.datasource.RateLocalDataSource
import ru.subbotind.android.rates.data.network.RateService
import ru.subbotind.android.rates.domain.entity.Rate
import ru.subbotind.android.rates.domain.repository.RateRepository
import javax.inject.Inject

class RateRepositoryImpl @Inject constructor(
    private val rateService: RateService,
    private val rateLocalDataSource: RateLocalDataSource
) : RateRepository {

    override fun getDailyRates(): Flow<List<Rate>> = rateLocalDataSource.getRates()

    override suspend fun fetchRates() {
        val valuteList = rateService.getDailyRates().valute.values.toList()
        rateLocalDataSource.updateRates(valuteList)
    }
}