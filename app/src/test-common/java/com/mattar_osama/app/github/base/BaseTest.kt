package com.mattar_osama.app.github.base

import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

abstract class BaseTest : KoinTest {

    // FOR DATA ---
    protected lateinit var mockServer: MockWebServer

    @Before
    open fun setUp() {
        configureMockServer()
    }

    @After
    open fun tearDown() {
        stopMockServer()
        stopKoin()
    }

    // MOCK SERVER ---
    abstract fun isMockServerEnabled(): Boolean // Because we don't want it always enabled on all tests

    private fun configureMockServer() {
        if (isMockServerEnabled()) {
            mockServer = MockWebServer()
            mockServer.start()
        }
    }

    private fun stopMockServer() {
        if (isMockServerEnabled()) {
            mockServer.shutdown()
        }
    }

    fun getMockUrl() = mockServer.url("/").toString()

}