package com.mattar_osama.app.github.ui.repository.search.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mattar_osama.app.github.R
import com.mattar_osama.app.github.data.api.NetworkState
import com.mattar_osama.app.github.data.dto.githubrepositorydto.ProjectDto

class SearchGithubReposAdapter(private val callback: OnClickListener) :
    PagedListAdapter<ProjectDto, RecyclerView.ViewHolder>(
        diffCallback
    ) {

    // FOR DATA ---
    private var networkState: NetworkState? = null

    interface OnClickListener {
        fun onClickRetry()
        fun whenListIsUpdated(size: Int, networkState: NetworkState?)
    }

    // OVERRIDE ---
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.item_search_github_repos -> SearchGithubReposViewHolder(
                view
            )
            R.layout.item_search_github_repos_network_state -> SearchGithubReposNetworkStateViewHolder(
                view
            )
            else -> throw IllegalArgumentException("Unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_search_github_repos -> (holder as SearchGithubReposViewHolder).bindTo(getItem(position))
            R.layout.item_search_github_repos_network_state -> (holder as SearchGithubReposNetworkStateViewHolder).bindTo(
                networkState,
                callback
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.item_search_github_repos_network_state
        } else {
            R.layout.item_search_github_repos
        }
    }

    override fun getItemCount(): Int {
        this.callback.whenListIsUpdated(super.getItemCount(), this.networkState)
        return super.getItemCount()
    }

    // UTILS ---
    private fun hasExtraRow() = networkState != null && networkState != NetworkState.SUCCESS

    // PUBLIC API ---
    fun updateNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<ProjectDto>() {
            override fun areItemsTheSame(oldItem: ProjectDto, newItem: ProjectDto): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: ProjectDto, newItem: ProjectDto): Boolean =
                oldItem == newItem
        }
    }
}