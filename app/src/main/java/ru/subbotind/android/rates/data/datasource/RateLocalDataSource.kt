package ru.subbotind.android.rates.data.datasource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import ru.subbotind.android.rates.data.dao.RateDao
import ru.subbotind.android.rates.data.datasource.converter.RateDbEntityConverter
import ru.subbotind.android.rates.data.entity.RateDbEntity
import ru.subbotind.android.rates.data.entity.RateModel
import ru.subbotind.android.rates.domain.entity.Rate
import javax.inject.Inject

class RateLocalDataSource @Inject constructor(
    private val rateDao: RateDao,
    private val rateDbEntityConverter: RateDbEntityConverter
) {

    fun getRates(): Flow<List<Rate>> =
        rateDao.getAllRates().mapLatest { movieEntityList ->
            movieEntityList.map { rateDbEntityConverter.fromRateEntity(it) }
        }

    suspend fun updateRates(rates: List<RateModel>) {
        val ratesEntity: List<RateDbEntity> = rates.map { rateModel ->
            rateDbEntityConverter.toRateEntity(rateModel)
        }

        rateDao.replaceAllRates(ratesEntity)
    }
}