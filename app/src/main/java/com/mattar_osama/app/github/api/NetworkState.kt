package com.mattar_osama.app.github.api

/**
 * Used to notify a client
 * of the actual state of a query
 */
enum class NetworkState {
    RUNNING,
    SUCCESS,
    FAILED
}