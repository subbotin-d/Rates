package ru.subbotind.android.rates.data.network

import retrofit2.http.GET
import ru.subbotind.android.rates.data.entity.DailyRatesModel

interface RateService {

    @GET("daily_json.js")
    suspend fun getDailyRates(): DailyRatesModel
}