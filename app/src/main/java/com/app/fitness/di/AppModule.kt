package com.app.fitness.di

import android.content.Context
import androidx.room.Room
import com.app.fitness.data.local.AppDatabase
import com.app.fitness.data.local.SessionDao
import com.app.fitness.data.repo.SessionRepoImpl
import com.app.fitness.domain.repo.SessionRepo
import com.app.fitness.service.location.LocationClient
import com.app.fitness.service.location.LocationUpdateImpl
import com.app.fitness.service.steps.StepsClient
import com.app.fitness.service.steps.StepsClientImpl
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

    @Provides
    @Singleton
    fun provideLocationClient(@ApplicationContext context: Context, fusedLocationProviderClient: FusedLocationProviderClient):LocationClient= LocationUpdateImpl(context,fusedLocationProviderClient)


    @Provides
    @Singleton
    fun provideStepClient(@ApplicationContext context: Context): StepsClient = StepsClientImpl(context)

    @Provides
    @Singleton
    fun provideFusedLocationClient(@ApplicationContext context: Context) = LocationServices.getFusedLocationProviderClient(context)


    @Provides
    @Singleton
    fun provideSessionRepo(sessionDao: SessionDao):SessionRepo=SessionRepoImpl(sessionDao)

}