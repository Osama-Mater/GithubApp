package com.mattar_osama.app.github.api

import com.mattar_osama.app.github.model.ProjectsResponseModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubReposService {

    @GET("search/repositories")
    fun search(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("sort") sort: String
    ): Deferred<ProjectsResponseModel>
}