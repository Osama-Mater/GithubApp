package com.mattar_osama.app.github.ui.repository.search.views

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.mattar_osama.app.github.domain.model.ProjectDomainModel
import kotlinx.android.synthetic.main.item_search_github_repos.view.*

class SearchGithubReposViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {

    // PUBLIC API ---
    fun bindTo(projectDto: ProjectDomainModel?) {
        projectDto?.let {
            loadImage(it.avatarUrl, itemView.item_search_github_repos_image_profile)
            itemView.item_search_github_repos_title.text = it.projectName.capitalize()
            itemView.item_search_github_repositories.text = it.projectDescription
        }
    }

    // UTILS ---
    private fun loadImage(url: String, imageView: ImageView) {
        Glide.with(itemView.context)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .apply(RequestOptions.circleCropTransform())
            .into(imageView)
    }
}