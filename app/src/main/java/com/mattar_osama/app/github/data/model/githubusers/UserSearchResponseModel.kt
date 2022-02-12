package com.mattar_osama.app.github.data.model.githubusers

import com.google.gson.annotations.SerializedName

data class UserSearchResponseModel(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val userProfiles: List<UserProfile>,
    @SerializedName("total_count")
    val totalCount: Int
)