package com.mattar_osama.app.github.ui.repository.search

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.bumptech.glide.Glide
import com.mattar_osama.app.github.R
import com.mattar_osama.app.github.api.NetworkState
import com.mattar_osama.app.github.base.BaseFragment
import com.mattar_osama.app.github.databinding.FragmentSearchGithubReposBinding
import com.mattar_osama.app.github.extensions.onQueryTextChange
import com.mattar_osama.app.github.ui.repository.search.views.SearchGithubReposAdapter
import com.mattar_osama.app.github.utils.convertFilterToIndex
import com.mattar_osama.app.github.utils.convertIndexToFilter

/**
 * A simple [Fragment] subclass.
 *
 */
class SearchGithubReposFragment : BaseFragment(), SearchGithubReposAdapter.OnClickListener {

    // FOR DATA ---
    private val viewModel by viewModels<SearchGithubReposViewModel>()
    private lateinit var adapter: SearchGithubReposAdapter

    private var _binding: FragmentSearchGithubReposBinding? = null
    private val binding get() = _binding!!

    // OVERRIDE ---
    override fun getLayoutId(): Int = R.layout.fragment_search_github_repos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchGithubReposBinding.inflate(
            inflater, container, false
        )
        return binding.root
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
        binding.fragmentSearchGithubReposEmptyListButton.setOnClickListener { viewModel.refreshAllList() }
        binding.fragmentSearchGithubReposFab.setOnClickListener { showDialogWithFilterItems { viewModel.refreshAllList() } }
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
        binding.fragmentSearchGithubReposRv.adapter = adapter
    }

    private fun configureObservables() {
        viewModel.networkState?.observe(
            viewLifecycleOwner,
            Observer { adapter.updateNetworkState(it) })
        viewModel.githubRepos.observe(viewLifecycleOwner, Observer { adapter.submitList(it) })
    }

    // UPDATE UI ----
    private fun updateUIWhenEmptyList(size: Int, networkState: NetworkState?) {
        binding.fragmentSearchGithubReposEmptyListImage.visibility = View.GONE
        binding.fragmentSearchGithubReposEmptyListButton.visibility = View.GONE
        binding.fragmentSearchGithubReposEmptyListTitle.visibility = View.GONE
        if (size == 0) {
            when (networkState) {
                NetworkState.SUCCESS -> {
                    Glide.with(this).load(R.drawable.ic_directions_run_black_24dp)
                        .into(binding.fragmentSearchGithubReposEmptyListImage)
                    binding.fragmentSearchGithubReposEmptyListTitle.text =
                        getString(R.string.no_result_found)
                    binding.fragmentSearchGithubReposEmptyListImage.visibility = View.VISIBLE
                    binding.fragmentSearchGithubReposEmptyListTitle.visibility = View.VISIBLE
                    binding.fragmentSearchGithubReposEmptyListButton.visibility = View.GONE
                }
                NetworkState.FAILED -> {
                    Glide.with(this).load(R.drawable.ic_healing_black_24dp)
                        .into(binding.fragmentSearchGithubReposEmptyListImage)
                    binding.fragmentSearchGithubReposEmptyListTitle.text =
                        getString(R.string.technical_error)
                    binding.fragmentSearchGithubReposEmptyListImage.visibility = View.VISIBLE
                    binding.fragmentSearchGithubReposEmptyListTitle.visibility = View.VISIBLE
                    binding.fragmentSearchGithubReposEmptyListButton.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun updateUIWhenLoading(size: Int, networkState: NetworkState?) {
        binding.fragmentSearchGithubReposProgress.visibility =
            if (size == 0 && networkState == NetworkState.RUNNING) View.VISIBLE else View.GONE
    }

    private fun showDialogWithFilterItems(callback: () -> Unit) {
        MaterialDialog(this.requireActivity()).show {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
