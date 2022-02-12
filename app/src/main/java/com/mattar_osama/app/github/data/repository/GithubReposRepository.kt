package com.mattar_osama.app.github.data.repository

import com.mattar_osama.app.github.data.api.GithubApiService
import com.mattar_osama.app.github.data.dto.githubrepositorydto.ProjectDto
import javax.inject.Inject

class GithubReposRepository @Inject constructor(private val service: GithubApiService) {

    private suspend fun search(query: String, page: Int, perPage: Int, sort: String) =
        service.search(query, page, perPage, sort).await()

    suspend fun searchGithubReposWithPagination(
        query: String,
        page: Int,
        perPage: Int,
        sort: String
    ): List<ProjectDto> {
        if (query.isEmpty()) return listOf()

        val request = search(query, page, perPage, sort) // Search by name
        return request.items
    }
}