package com.mattar_osama.app.github.storage

import android.content.Context
import android.content.SharedPreferences
import com.mattar_osama.app.github.data.dto.githubrepositorydto.Filters
import com.mattar_osama.app.github.extensions.setValue
import javax.inject.Inject

class SharedPrefsManager @Inject constructor(private val context: Context) {

    private fun get(): SharedPreferences =
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)

    /**
     * Returns [Filters.ResultSearchRepositories] saved in [SharedPreferences]
     * This filter will be used to sort "search" queries
     */
    fun getFilterWhenSearchingRepos(): Filters.ResultSearchRepositories {
        val value = get().getString(KEY_FILTERS, Filters.ResultSearchRepositories.BY_STARS.value)
        return when (value) {
            Filters.ResultSearchRepositories.BY_STARS.value -> Filters.ResultSearchRepositories.BY_STARS
            Filters.ResultSearchRepositories.BY_FORKS.value -> Filters.ResultSearchRepositories.BY_FORKS
            Filters.ResultSearchRepositories.BY_UPDATED.value -> Filters.ResultSearchRepositories.BY_UPDATED
            else -> throw IllegalStateException("Filter not recognized")
        }
    }

    /**
     * Saves [Filters.ResultSearchRepositories] in [SharedPreferences]
     * This filter will be used to sort "search" queries
     */
    fun saveFilterWhenSearchingGithubRepos(filters: Filters.ResultSearchRepositories) {
        get().setValue(KEY_FILTERS, filters.value)
    }

    companion object {
        private const val SP_NAME = "GithubAppPrefs"
        private const val KEY_FILTERS = "KEY_FILTERS"
    }
}