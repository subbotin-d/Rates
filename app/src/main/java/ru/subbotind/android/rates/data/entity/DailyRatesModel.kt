package ru.subbotind.android.rates.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyRatesModel(

    @SerialName("Valute")
    val valute: Map<String, RateModel>
)
