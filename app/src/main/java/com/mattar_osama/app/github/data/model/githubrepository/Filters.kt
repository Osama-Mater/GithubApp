package com.mattar_osama.app.github.data.model.githubrepository

import com.mattar_osama.app.github.data.api.GithubReposService

object Filters {

    /**
     * Filters used by [GithubReposService]
     * to sort "search" queries
     */
    enum class ResultSearchRepositories(val value: String) {
        BY_STARS("stars"), BY_FORKS("forks"), BY_UPDATED("updated")
    }
}