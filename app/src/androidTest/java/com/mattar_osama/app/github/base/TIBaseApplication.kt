package com.mattar_osama.app.github.base

import android.app.Application
import com.mattar_osama.app.github.GithubApplication

/**
 * We use a separate [Application] for tests to prevent initializing release modules.
 * On the contrary, we will provide inside our tests custom [Module] directly.
 */
class TIBaseApplication : GithubApplication() {
}