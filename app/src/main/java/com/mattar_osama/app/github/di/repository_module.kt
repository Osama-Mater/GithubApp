package com.mattar_osama.app.github.di

import com.mattar_osama.app.github.repository.GithubReposRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory { GithubReposRepository(get()) }
}