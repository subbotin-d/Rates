package ru.subbotind.android.rates.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import ru.subbotind.android.rates.data.network.RateService
import ru.subbotind.android.rates.data.repository.RateRepositoryImpl
import ru.subbotind.android.rates.domain.repository.RateRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RatesModule {

    companion object {
        @Provides
        @Singleton
        fun provideRateService(retrofit: Retrofit): RateService =
            retrofit.create()
    }

    @Binds
    @Singleton
    fun bindRatesRepository(ratesRepository: RateRepositoryImpl): RateRepository
}