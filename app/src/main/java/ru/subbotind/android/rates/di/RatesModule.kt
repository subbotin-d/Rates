package ru.subbotind.android.rates.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import ru.subbotind.android.rates.data.RateRepositoryImpl
import ru.subbotind.android.rates.data.dao.RateDao
import ru.subbotind.android.rates.data.database.RatesDataBase
import ru.subbotind.android.rates.data.network.RateService
import ru.subbotind.android.rates.domain.repository.RateRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RatesModule {

    companion object {
        @Provides
        @Singleton
        fun provideMovieService(retrofit: Retrofit): RateService =
            retrofit.create()

        @Provides
        @Singleton
        fun provideRateDao(ratesDataBase: RatesDataBase): RateDao =
            ratesDataBase.ratesDao()
    }

    @Binds
    @Singleton
    fun bindRatesRepository(ratesRepository: RateRepositoryImpl): RateRepository
}