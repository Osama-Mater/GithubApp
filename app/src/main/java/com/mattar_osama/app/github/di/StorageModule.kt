package com.mattar_osama.app.github.di

import android.content.Context
import com.mattar_osama.app.github.storage.SharedPrefsManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {
    @Singleton
    @Provides
    fun provideStorageManager(@ApplicationContext appContext: Context): SharedPrefsManager =
        SharedPrefsManager(appContext)
}