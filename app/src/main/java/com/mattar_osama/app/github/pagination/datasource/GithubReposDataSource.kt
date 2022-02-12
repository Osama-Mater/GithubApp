package com.mattar_osama.app.github.pagination.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.mattar_osama.app.github.data.api.NetworkState
import com.mattar_osama.app.github.data.dto.githubrepositorydto.ProjectDto
import com.mattar_osama.app.github.data.repository.GithubReposRepository
import kotlinx.coroutines.*

class GithubReposDataSource(
    private val repository: GithubReposRepository,
    private val query: String,
    private val sort: String,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, ProjectDto>() {

    // FOR DATA ---
    private var supervisorJob = SupervisorJob()
    private val networkState = MutableLiveData<NetworkState>()
    private var retryQuery: (() -> Any)? =
        null // Keep reference of the last query (to be able to retry it if necessary)

    // OVERRIDE ---
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ProjectDto>
    ) {
        retryQuery = { loadInitial(params, callback) }
        executeQuery(1, params.requestedLoadSize) {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ProjectDto>) {
        val page = params.key
        retryQuery = { loadAfter(params, callback) }
        executeQuery(page, params.requestedLoadSize) {
            callback.onResult(it, page + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ProjectDto>) {}

    // UTILS ---
    private fun executeQuery(page: Int, perPage: Int, callback: (List<ProjectDto>) -> Unit) {
        networkState.postValue(NetworkState.RUNNING)
        scope.launch(getJobErrorHandler() + supervisorJob) {
            delay(200) // To handle user typing case
            val githubRepository =
                repository.searchGithubReposWithPagination(query, page, perPage, sort)
            retryQuery = null
            networkState.postValue(NetworkState.SUCCESS)
            callback(githubRepository)
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        Log.e(GithubReposDataSource::class.java.simpleName, "An error happened: $e")
        networkState.postValue(NetworkState.FAILED)
    }

    override fun invalidate() {
        super.invalidate()
        supervisorJob.cancelChildren()   // Cancel possible running job to only keep last result searched by user
    }

    // PUBLIC API ---

    fun getNetworkState(): LiveData<NetworkState> =
        networkState

    fun refresh() =
        this.invalidate()

    fun retryFailedQuery() {
        val prevQuery = retryQuery
        retryQuery = null
        prevQuery?.invoke()
    }
}