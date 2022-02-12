package com.mattar_osama.app.github.data.api

import com.mattar_osama.app.github.data.model.githubrepository.ProjectsResponseModel
import com.mattar_osama.app.github.data.model.githubusers.UserSearchResponseModel
import com.mattar_osama.app.github.data.model.userprofile.GitHubUserProfile
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubReposService {

    @GET("search/repositories")
    fun search(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("sort") sort: String
    ): Deferred<ProjectsResponseModel>

    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): List<UserSearchResponseModel>

    @GET("users/{username}")
    suspend fun userProfile(@Path("name") name: String): GitHubUserProfile
}