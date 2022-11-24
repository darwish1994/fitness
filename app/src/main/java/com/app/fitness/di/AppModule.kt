package com.app.fitness.di

import android.content.Context
import androidx.room.Room
import com.app.fitness.data.local.AppDatabase
import com.app.fitness.data.local.TrackingDao
import com.app.fitness.service.location.LocationClient
import com.app.fitness.service.location.LocationUpdateImpl
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    companion object {
        private const val DATA_BASE_NAME = "fitness.db"
    }

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            DATA_BASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideTrackingDao(appDatabase: AppDatabase):TrackingDao=appDatabase.fitnessDea()

    @Singleton
    @Provides
    fun provideFusedLocationClient(@ApplicationContext context: Context) = LocationServices.getFusedLocationProviderClient(context)

    @Singleton
    @Provides
    fun provideLocationClient(@ApplicationContext context: Context,fusedLocationProviderClient: FusedLocationProviderClient)= LocationUpdateImpl(context,fusedLocationProviderClient)


}