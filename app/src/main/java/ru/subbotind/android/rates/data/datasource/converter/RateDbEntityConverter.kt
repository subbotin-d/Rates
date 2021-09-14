package ru.subbotind.android.rates.data.datasource.converter

import ru.subbotind.android.rates.data.entity.RateDbEntity
import ru.subbotind.android.rates.data.entity.RateModel
import ru.subbotind.android.rates.domain.entity.Rate
import javax.inject.Inject

class RateDbEntityConverter @Inject constructor() {

    fun fromRateEntity(rateDbEntity: RateDbEntity): Rate = Rate(
        code = rateDbEntity.charCode,
        nominal = rateDbEntity.nominal,
        name = rateDbEntity.name,
        value = rateDbEntity.value
    )

    fun toRateEntity(rateModel: RateModel): RateDbEntity = RateDbEntity(
        id = rateModel.numCode.toInt(),
        charCode = rateModel.charCode,
        nominal = rateModel.nominal,
        name = rateModel.name,
        value = rateModel.value
    )
}