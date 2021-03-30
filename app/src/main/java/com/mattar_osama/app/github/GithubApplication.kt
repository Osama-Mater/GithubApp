package com.mattar_osama.app.github

import android.app.Application
import com.mattar_osama.app.github.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

open class GithubApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        configureDi()
    }

    // CONFIGURATION ---
    open fun configureDi() = startKoin {
        androidLogger()
        androidContext(this@GithubApplication)
        modules(provideComponent())
    }

    // PUBLIC API ---
    open fun provideComponent() = appComponent
}