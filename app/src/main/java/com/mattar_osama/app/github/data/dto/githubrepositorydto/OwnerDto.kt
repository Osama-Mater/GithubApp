package com.mattar_osama.app.github.data.dto.githubrepositorydto

import com.google.gson.annotations.SerializedName


data class OwnerDto(
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String
)
