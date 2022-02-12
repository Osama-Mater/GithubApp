package com.mattar_osama.app.github.data.repository

import com.mattar_osama.app.github.data.api.GithubReposService
import com.mattar_osama.app.github.data.model.githubrepository.ProjectModel
import javax.inject.Inject

class GithubReposRepository @Inject constructor(private val service: GithubReposService) {

    private suspend fun search(query: String, page: Int, perPage: Int, sort: String) =
        service.search(query, page, perPage, sort).await()

    suspend fun searchGithubReposWithPagination(
        query: String,
        page: Int,
        perPage: Int,
        sort: String
    ): List<ProjectModel> {
        if (query.isEmpty()) return listOf()

        val request = search(query, page, perPage, sort) // Search by name
        return request.items
    }
}