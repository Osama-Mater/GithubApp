package com.mattar_osama.app.github.pagination.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.mattar_osama.app.github.data.api.NetworkState
import com.mattar_osama.app.github.domain.model.ProjectDomainModel
import com.mattar_osama.app.github.domain.usecase.SearchGithubRepositories
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class GithubReposDataSource(
    private val searchGithubRepositories: SearchGithubRepositories,
    private val query: String,
    private val sort: String,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, ProjectDomainModel>() {

    // FOR DATA ---
    private var supervisorJob = SupervisorJob()
    private val networkState = MutableLiveData<NetworkState>()
    private var retryQuery: (() -> Any)? =
        null // Keep reference of the last query (to be able to retry it if necessary)

    // OVERRIDE ---
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ProjectDomainModel>
    ) {
        retryQuery = { loadInitial(params, callback) }
        executeQuery(1, params.requestedLoadSize) {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ProjectDomainModel>
    ) {
        val page = params.key
        retryQuery = { loadAfter(params, callback) }
        executeQuery(page, params.requestedLoadSize) {
            callback.onResult(it, page + 1)
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ProjectDomainModel>
    ) {
    }

    // UTILS ---
    private fun executeQuery(
        page: Int,
        perPage: Int,
        callback: (List<ProjectDomainModel>) -> Unit
    ) {
        networkState.postValue(NetworkState.RUNNING)
        scope.launch(getJobErrorHandler() + supervisorJob) {
            delay(200) // To handle user typing case
            val githubRepository =
                searchGithubRepositories.execute(query, page, sort)
            retryQuery = null
            networkState.postValue(NetworkState.SUCCESS)
            githubRepository.collect {
                callback(it)
            }
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