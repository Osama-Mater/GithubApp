package com.mattar_osama.app.github.data.repository.model

data class GithubUserDetailsRepositoryModel(
    val avatarUrl: String,
    val bio: String?,
    val blog: String,
    val company: String,
    val email: String,
    val followers: Int,
    val following: Int,
    val location: String,
    val login: String,
    val name: String,
    val twitter_username: String,
)
