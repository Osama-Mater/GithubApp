package com.mattar_osama.app.github.repository

import com.mattar_osama.app.github.base.BaseUT
import com.mattar_osama.app.github.di.configureAppComponent
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import retrofit2.HttpException
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class GithubReposRepositoryTest : BaseUT() {

    // FOR DATA ---
    private val githubReposRepository by inject<GithubReposRepository>()

    // OVERRIDE ---
    override fun isMockServerEnabled() = true

    override fun setUp() {
        super.setUp()
        startKoin {
            configureAppComponent(getMockUrl())
        }
    }

    // TESTS ---

    @Test
    fun `search repos by name and succeed`() {
        mockHttpResponse("search_repos.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val repos = githubReposRepository.searchGithubReposWithPagination("FAKE", -1, -1, "FAKE")
            assertEquals(1, repos.size)
        }
    }

    @Test(expected = HttpException::class)
    fun `search repos by name and fail`() {
        mockHttpResponse("search_repos.json", HttpURLConnection.HTTP_FORBIDDEN)
        runBlocking {
            githubReposRepository.searchGithubReposWithPagination("FAKE", -1, -1, "FAKE")
        }
    }
}