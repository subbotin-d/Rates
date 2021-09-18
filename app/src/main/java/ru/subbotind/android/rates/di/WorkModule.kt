package ru.subbotind.android.rates.di

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkRequest
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.subbotind.android.rates.background.RateSynchronizationWorker
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object WorkModule {

    private const val PERIOD_IN_MINUTES = 2L

    @Provides
    fun provideJobConstraints(): Constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    @Provides
    fun provideJobRequest(jobConstraints: Constraints): WorkRequest =
        PeriodicWorkRequestBuilder<RateSynchronizationWorker>(
            PERIOD_IN_MINUTES,
            TimeUnit.MINUTES
        )
            .setConstraints(jobConstraints)
            .build()
}