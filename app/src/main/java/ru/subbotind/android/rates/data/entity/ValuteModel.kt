package ru.subbotind.android.rates.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ValuteModel(

    @SerialName("USD")
    val usd: RateModel,

    @SerialName("EUR")
    val eur: RateModel
)