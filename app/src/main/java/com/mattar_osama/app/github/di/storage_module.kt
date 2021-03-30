package com.mattar_osama.app.github.di

import com.mattar_osama.app.github.storage.SharedPrefsManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val storageModule = module {
    single { SharedPrefsManager(androidContext()) }
}