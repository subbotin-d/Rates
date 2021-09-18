package ru.subbotind.android.rates.domain.repository

import ru.subbotind.android.rates.domain.entity.Rate

interface RateRepository {

    suspend fun getDailyRates(): List<Rate>
}