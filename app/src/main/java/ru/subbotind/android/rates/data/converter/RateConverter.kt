package ru.subbotind.android.rates.data.converter

import ru.subbotind.android.rates.data.entity.RateModel
import ru.subbotind.android.rates.domain.entity.Rate
import javax.inject.Inject

class RateConverter @Inject constructor() {

    fun convert(rateModel: RateModel): Rate {
        val rateChangingWith4DigitsPrecision =
            String.format("%.4f", rateModel.value - rateModel.previous).toDouble()

        return Rate(
            code = rateModel.charCode,
            nominal = rateModel.nominal,
            name = rateModel.name,
            value = rateModel.value,
            change = rateChangingWith4DigitsPrecision
        )
    }
}