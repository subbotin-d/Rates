package ru.subbotind.android.rates.data.repository

import ru.subbotind.android.rates.data.converter.RateConverter
import ru.subbotind.android.rates.data.network.RateService
import ru.subbotind.android.rates.domain.entity.Rate
import ru.subbotind.android.rates.domain.repository.RateRepository
import javax.inject.Inject

class RateRepositoryImpl @Inject constructor(
    private val rateService: RateService,
    private val rateConverter: RateConverter
) : RateRepository {

    override suspend fun getDailyRates(): List<Rate> =
        rateService.getDailyRates().valute.values.toList().map { rateModel ->
            rateConverter.convert(rateModel)
        }
}