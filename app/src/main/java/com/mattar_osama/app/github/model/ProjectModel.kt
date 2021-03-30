package com.mattar_osama.app.github.model

import com.google.gson.annotations.SerializedName

class ProjectModel(
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String,
        val full_name: String,
        val stargazers_count: Int,
        val created_at: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("owner")
        val owner: OwnerModel
)
