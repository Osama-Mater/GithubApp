package com.mattar_osama.app.github

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.mattar_osama.app.github.base.TIBaseApplication

/**
 * Custom runner to disable dependency injection.
 */
class TIRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, TIBaseApplication::class.java.name, context)
    }
}