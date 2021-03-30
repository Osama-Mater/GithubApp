package com.mattar_osama.app.github.di

import androidx.test.platform.app.InstrumentationRegistry
import com.mattar_osama.app.github.storage.SharedPrefsManager
import org.koin.dsl.module

val storageModuleTest = module {
    single { SharedPrefsManager(InstrumentationRegistry.getInstrumentation().context) }
}