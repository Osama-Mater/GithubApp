package com.mattar_osama.app.github.ui.repository.search

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mattar_osama.app.github.api.NetworkState
import com.mattar_osama.app.github.base.BaseViewModel
import com.mattar_osama.app.github.model.Filters
import com.mattar_osama.app.github.model.ProjectModel
import com.mattar_osama.app.github.pagination.datasource.GithubReposDataSourceFactory
import com.mattar_osama.app.github.repository.GithubReposRepository
import com.mattar_osama.app.github.storage.SharedPrefsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchGithubReposViewModel @Inject constructor(
    private val repository: GithubReposRepository,
    private val sharedPrefsManager: SharedPrefsManager
) : BaseViewModel() {

    // FOR DATA ---
    private val githubReposDataSource =
        GithubReposDataSourceFactory(repository = repository, scope = ioScope)

    // OBSERVABLES ---
    val githubRepos = LivePagedListBuilder(githubReposDataSource, pagedListConfig()).build()
    val networkState: LiveData<NetworkState>? =
        switchMap(githubReposDataSource.source) { it.getNetworkState() }

    // PUBLIC API ---

    /**
     * Fetch a list of [ProjectModel] by name
     * Called each time an user hits a key through [SearchView].
     */
    fun fetchReposByName(query: String) {
        val search = query.trim()
        if (githubReposDataSource.getQuery() == search) return
        githubReposDataSource.updateQuery(
            search,
            sharedPrefsManager.getFilterWhenSearchingRepos().value
        )
    }

    /**
     * Retry possible last paged request (ie: network issue)
     */
    fun refreshFailedRequest() =
        githubReposDataSource.getSource()?.retryFailedQuery()

    /**
     * Refreshes all list after an issue
     */
    fun refreshAllList() =
        githubReposDataSource.getSource()?.refresh()

    /**
     * Returns filter [Filters.ResultSearchRepositories] used to sort "search" request
     */
    fun getFilterWhenSearchingRepos() =
        sharedPrefsManager.getFilterWhenSearchingRepos()

    /**
     * Saves filter [Filters.ResultSearchRepositories] used to sort "search" request
     */
    fun saveFilterWhenSearchingRepos(filter: Filters.ResultSearchRepositories) =
        sharedPrefsManager.saveFilterWhenSearchingGithubRepos(filter)

    /**
     * Returns current search query
     */
    fun getCurrentQuery() =
        githubReposDataSource.getQuery()

    // UTILS ---

    private fun pagedListConfig() = PagedList.Config.Builder()
        .setInitialLoadSizeHint(5)
        .setEnablePlaceholders(false)
        .setPageSize(5 * 2)
        .build()
}