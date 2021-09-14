package ru.subbotind.android.rates.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.subbotind.android.rates.domain.entity.Rate

interface RateRepository {

    fun getDailyRates(): Flow<List<Rate>>
    suspend fun fetchRates()
}