package ru.subbotind.android.rates.domain.entity

data class Rate(
    val code: String,
    val nominal: Int,
    val name: String,
    val value: Double,
    val change: Double
)