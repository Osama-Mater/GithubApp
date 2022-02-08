package com.mattar_osama.app.github.ui.repository.search

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.bumptech.glide.Glide
import com.mattar_osama.app.github.R
import com.mattar_osama.app.github.api.NetworkState
import com.mattar_osama.app.github.base.BaseFragment
import com.mattar_osama.app.github.extensions.onQueryTextChange
import com.mattar_osama.app.github.ui.repository.search.views.SearchGithubReposAdapter
import com.mattar_osama.app.github.utils.convertFilterToIndex
import com.mattar_osama.app.github.utils.convertIndexToFilter
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 *
 */
class SearchGithubReposFragment : BaseFragment(), SearchGithubReposAdapter.OnClickListener {

    // FOR DATA ---
    private val viewModel: SearchGithubReposViewModel by viewModel()
    private lateinit var adapter: SearchGithubReposAdapter

    // OVERRIDE ---
    override fun getLayoutId(): Int = R.layout.fragment_search_github_repos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()
        configureObservables()
        configureOnClick()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        configureMenu(menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    // ACTION ---
    override fun onClickRetry() {
        viewModel.refreshFailedRequest()
    }

    override fun whenListIsUpdated(size: Int, networkState: NetworkState?) {
        updateUIWhenLoading(size, networkState)
        updateUIWhenEmptyList(size, networkState)
    }

    private fun configureOnClick() {
        fragment_search_github_repos_empty_list_button.setOnClickListener { viewModel.refreshAllList() }
        fragment_search_github_repos_fab.setOnClickListener { showDialogWithFilterItems { viewModel.refreshAllList() } }
    }

    // CONFIGURATION ---
    private fun configureMenu(menu: Menu) {
        val searchMenuItem = menu.findItem(R.id.action_search)
        val possibleExistingQuery = viewModel.getCurrentQuery()
        val searchView = searchMenuItem.actionView as SearchView
        if (possibleExistingQuery != null && !possibleExistingQuery.isEmpty()) {
            searchMenuItem.expandActionView()
            searchView.setQuery(possibleExistingQuery, false)
            searchView.clearFocus()
        }
        searchView.onQueryTextChange {
            viewModel.fetchReposByName(it)
        }
    }

    private fun configureRecyclerView() {
        adapter = SearchGithubReposAdapter(this)
        fragment_search_github_repos_rv.adapter = adapter
    }

    private fun configureObservables() {
        viewModel.networkState?.observe(this, Observer { adapter.updateNetworkState(it) })
        viewModel.githubRepos.observe(this, Observer { adapter.submitList(it) })
    }

    // UPDATE UI ----
    private fun updateUIWhenEmptyList(size: Int, networkState: NetworkState?) {
        fragment_search_github_repos_empty_list_image.visibility = View.GONE
        fragment_search_github_repos_empty_list_button.visibility = View.GONE
        fragment_search_github_repos_empty_list_title.visibility = View.GONE
        if (size == 0) {
            when (networkState) {
                NetworkState.SUCCESS -> {
                    Glide.with(this).load(R.drawable.ic_directions_run_black_24dp)
                        .into(fragment_search_github_repos_empty_list_image)
                    fragment_search_github_repos_empty_list_title.text =
                        getString(R.string.no_result_found)
                    fragment_search_github_repos_empty_list_image.visibility = View.VISIBLE
                    fragment_search_github_repos_empty_list_title.visibility = View.VISIBLE
                    fragment_search_github_repos_empty_list_button.visibility = View.GONE
                }
                NetworkState.FAILED -> {
                    Glide.with(this).load(R.drawable.ic_healing_black_24dp)
                        .into(fragment_search_github_repos_empty_list_image)
                    fragment_search_github_repos_empty_list_title.text =
                        getString(R.string.technical_error)
                    fragment_search_github_repos_empty_list_image.visibility = View.VISIBLE
                    fragment_search_github_repos_empty_list_title.visibility = View.VISIBLE
                    fragment_search_github_repos_empty_list_button.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun updateUIWhenLoading(size: Int, networkState: NetworkState?) {
        fragment_search_github_repos_progress.visibility =
            if (size == 0 && networkState == NetworkState.RUNNING) View.VISIBLE else View.GONE
    }

    private fun showDialogWithFilterItems(callback: () -> Unit) {
        MaterialDialog(this.activity!!).show {
            title(R.string.filter_popup_title)
            listItemsSingleChoice(
                R.array.filters,
                initialSelection = convertFilterToIndex(viewModel.getFilterWhenSearchingRepos())
            ) { _, index, _ ->
                viewModel.saveFilterWhenSearchingRepos(convertIndexToFilter(index))
            }
            positiveButton {
                callback()
            }
        }
    }
}
