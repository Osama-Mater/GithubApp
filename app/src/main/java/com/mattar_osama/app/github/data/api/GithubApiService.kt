package com.mattar_osama.app.github.data.api

import com.mattar_osama.app.github.data.dto.githubrepositorydto.ProjectsResponseDto
import com.mattar_osama.app.github.data.dto.githubusersdto.UserSearchResponseDto
import com.mattar_osama.app.github.data.dto.userprofiledto.GitHubUserProfileDto
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {

    @GET("search/repositories")
    fun search(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("sort") sort: String
    ): Deferred<ProjectsResponseDto>

    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 10,
        @Query("sort") sort: String
    ): ProjectsResponseDto

    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 10,
    ): UserSearchResponseDto

    @GET("users/{username}")
    suspend fun userProfile(@Path("name") name: String): GitHubUserProfileDto
}