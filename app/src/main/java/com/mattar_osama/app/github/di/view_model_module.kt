package com.mattar_osama.app.github.di

import com.mattar_osama.app.github.ui.repository.search.SearchGithubReposViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SearchGithubReposViewModel(get(), get()) }
}
