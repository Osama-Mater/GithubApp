package com.mattar_osama.app.github

import android.app.Application
import com.mattar_osama.app.github.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

open class GithubApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(level = Level.ERROR)
            androidContext(this@GithubApplication)
            modules(appComponent)
        }
    }
}