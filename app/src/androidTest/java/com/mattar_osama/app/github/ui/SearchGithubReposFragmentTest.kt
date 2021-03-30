package com.mattar_osama.app.github.ui

import android.widget.AutoCompleteTextView
import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.mattar_osama.app.github.R
import com.mattar_osama.app.github.base.BaseIT
import com.mattar_osama.app.github.di.configureAppComponent
import com.mattar_osama.app.github.di.storageModuleTest
import com.mattar_osama.app.github.ui.repository.search.SearchGithubReposFragment
import com.mattar_osama.app.github.utils.hasItemCount
import com.mattar_osama.app.github.utils.waitForAdapterChangeWithPagination
import org.hamcrest.CoreMatchers.*
import org.junit.*
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import java.net.HttpURLConnection


@RunWith(AndroidJUnit4::class)
@LargeTest
class SearchGithubReposFragmentTest : BaseIT() {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java, true, false)

    @get:Rule
    var executorRule = CountingTaskExecutorRule()

    // OVERRIDE ---
    override fun isMockServerEnabled() = true

    @Before
    override fun setUp() {
        super.setUp()
        configureCustomDependencies()
        activityRule.launchActivity(null)
    }

    // TESTS ---

    @Test
    fun whenFragmentIsEmpty() {
        onView(withId(R.id.fragment_search_github_repos_empty_list_image)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_search_github_repos_empty_list_title)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_search_github_repos_empty_list_title)).check(
            matches(
                withText(
                    containsString(getString(R.string.no_result_found))
                )
            )
        )
        onView(withId(R.id.fragment_search_github_repos_empty_list_button)).check(
            matches(
                not(
                    isDisplayed()
                )
            )
        )
    }

    @Test
    fun whenUserSearchReposAndSucceed() {
        mockHttpResponse("search_repos.json", HttpURLConnection.HTTP_OK)

        onView(withId(R.id.action_search)).perform(click())
        onView(isAssignableFrom(AutoCompleteTextView::class.java)).perform(typeText("t"))
        waitForAdapterChangeWithPagination(getRecyclerView(), executorRule, 4)

        onView(withId(R.id.fragment_search_github_repos_rv)).check(matches((hasItemCount(1))))
        onView(
            allOf(
                withId(R.id.item_search_github_repos_title),
                withText("Android")
            )
        ).check(
            matches(isDisplayed())
        )
        onView(
            allOf(
                withId(R.id.item_search_github_repositories),
                withText("GitHub上最火的Android开源项目,所有开源项目都有详细资料和配套视频")
            )
        )
    }

    @Test
    fun whenUserSearchReposAndFailed() {
        mockHttpResponse("search_repos.json", HttpURLConnection.HTTP_BAD_REQUEST)

        onView(withId(R.id.action_search)).perform(click())
        onView(isAssignableFrom(AutoCompleteTextView::class.java)).perform(typeText("t"))
        Thread.sleep(1000)
        onView(withId(R.id.fragment_search_github_repos_empty_list_image)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_search_github_repos_empty_list_title)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_search_github_repos_empty_list_title)).check(
            matches(
                withText(
                    containsString(getString(R.string.technical_error))
                )
            )
        )
        onView(withId(R.id.fragment_search_github_repos_empty_list_button)).check(
            matches(
                isDisplayed()
            )
        )
    }

    // UTILS ---

    /**
     * Configure custom [Module] for each [Test]
     */
    private fun configureCustomDependencies() {
        loadKoinModules(
            configureAppComponent(getMockUrl()).toMutableList().apply { add(storageModuleTest) })
    }

    /**
     * Convenient access to String resources
     */
    private fun getString(id: Int) = activityRule.activity.getString(id)

    /**
     * Convenient access to [SearchGithubReposFragment]'s RecyclerView
     */
    private fun getRecyclerView() =
        activityRule.activity.findViewById<RecyclerView>(R.id.fragment_search_github_repos_rv)
}