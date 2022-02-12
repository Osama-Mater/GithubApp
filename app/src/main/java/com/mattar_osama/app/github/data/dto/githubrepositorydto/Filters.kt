package com.mattar_osama.app.github.data.dto.githubrepositorydto

import com.mattar_osama.app.github.data.api.GithubApiService

object Filters {

    /**
     * Filters used by [GithubApiService]
     * to sort "search" queries
     */
    enum class ResultSearchRepositories(val value: String) {
        BY_STARS("stars"), BY_FORKS("forks"), BY_UPDATED("updated")
    }
}