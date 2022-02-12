package com.mattar_osama.app.github.utils

import com.mattar_osama.app.github.data.dto.githubrepositorydto.Filters

fun convertFilterToIndex(filter: Filters.ResultSearchRepositories) = when (filter) {
    Filters.ResultSearchRepositories.BY_UPDATED -> 2
    Filters.ResultSearchRepositories.BY_FORKS -> 1
    Filters.ResultSearchRepositories.BY_STARS -> 0
}

fun convertIndexToFilter(index: Int) = when (index) {
    2 -> Filters.ResultSearchRepositories.BY_UPDATED
    1 -> Filters.ResultSearchRepositories.BY_FORKS
    0 -> Filters.ResultSearchRepositories.BY_STARS
    else -> throw IllegalStateException("Index not recognized")
}