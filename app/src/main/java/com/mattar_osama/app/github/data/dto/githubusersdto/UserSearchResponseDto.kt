package com.mattar_osama.app.github.data.dto.githubusersdto

import com.google.gson.annotations.SerializedName

data class UserSearchResponseDto(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val userProfiles: List<UserProfileDto>,
    @SerializedName("total_count")
    val totalCount: Int
)