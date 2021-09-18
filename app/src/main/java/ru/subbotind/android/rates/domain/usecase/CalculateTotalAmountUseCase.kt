package ru.subbotind.android.rates.domain.usecase

import javax.inject.Inject

class CalculateTotalAmountUseCase @Inject constructor() {

    operator fun invoke(count: Int, rate: Double, nominal: Int): Double = (count * rate) / nominal
}