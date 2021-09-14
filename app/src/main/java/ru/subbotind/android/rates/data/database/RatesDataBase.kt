package ru.subbotind.android.rates.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.subbotind.android.rates.data.dao.RateDao
import ru.subbotind.android.rates.data.entity.RateDbEntity

@Database(entities = [RateDbEntity::class], version = 1)
abstract class RatesDataBase : RoomDatabase() {
    abstract fun ratesDao(): RateDao
}