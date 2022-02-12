package com.mattar_osama.app.github.pagination.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.mattar_osama.app.github.domain.model.ProjectDomainModel
import com.mattar_osama.app.github.domain.usecase.SearchGithubRepositories
import kotlinx.coroutines.CoroutineScope

class GithubReposDataSourceFactory(
    private val searchGithubRepositories: SearchGithubRepositories,
    private var query: String = "",
    private var sort: String = "",
    private val scope: CoroutineScope
) : DataSource.Factory<Int, ProjectDomainModel>() {

    val source = MutableLiveData<GithubReposDataSource>()

    override fun create(): DataSource<Int, ProjectDomainModel> {
        val source = GithubReposDataSource(searchGithubRepositories, query, sort, scope)
        this.source.postValue(source)
        return source
    }

    // --- PUBLIC API

    fun getQuery() = query

    fun getSource() = source.value

    fun updateQuery(query: String, sort: String) {
        this.query = query
        this.sort = sort
        getSource()?.refresh()
    }
}