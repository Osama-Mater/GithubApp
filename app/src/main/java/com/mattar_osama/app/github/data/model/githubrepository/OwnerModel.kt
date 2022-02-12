package com.mattar_osama.app.github.data.model.githubrepository

import com.google.gson.annotations.SerializedName


data class OwnerModel(
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String
)
