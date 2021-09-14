package ru.subbotind.android.rates.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.subbotind.android.rates.data.entity.RateDbEntity

@Dao
interface RateDao {

    @Update
    suspend fun updateRate(rate: RateDbEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun replaceAllRates(rates: List<RateDbEntity>)

    @Query("SELECT * FROM rate_table")
    fun getAllRates(): Flow<List<RateDbEntity>>

    @Query("SELECT * FROM rate_table WHERE id=:id")
    fun getRate(id: Int): Flow<RateDbEntity>
}