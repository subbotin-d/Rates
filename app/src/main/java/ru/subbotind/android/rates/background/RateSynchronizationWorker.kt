package ru.subbotind.android.rates.background

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import ru.subbotind.android.rates.domain.repository.RateRepository

@HiltWorker
class RateSynchronizationWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val rateRepository: RateRepository
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result = try {
        rateRepository.getDailyRates()
        Result.success()
    } catch (e: Throwable) {
        Result.failure()
    }
}