package com.mattar_osama.app.github.di

fun configureAppComponent(baseApi: String) = listOf(
    configureNetworkModuleForTest(baseApi),
    viewModelModule,
    repositoryModule
)