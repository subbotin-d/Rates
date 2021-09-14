package ru.subbotind.android.rates.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.subbotind.android.rates.data.database.RatesDataBase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMovieDataBase(@ApplicationContext context: Context): RatesDataBase =
        Room.databaseBuilder(
            context,
            RatesDataBase::class.java,
            "rate_database"
        )
            .fallbackToDestructiveMigration()
            .build()
}