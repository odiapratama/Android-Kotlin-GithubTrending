package com.problemsolver.githubtrending.ui.trending

import EspressoIdlingResource
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import com.problemsolver.githubtrending.R
import com.problemsolver.githubtrending.models.Trending
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*


class TrendingInstrumentedTest {

    private lateinit var timer: Timer

    @get:Rule
    var activityRule = IntentsTestRule(TrendingActivity::class.java)

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource())
    }

    @Before
    fun setUp() {
        timer = Timer()
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource())
    }

    @Test
    fun onResume() {
        assertEquals(true, ::timer.isInitialized)
    }

    @Test
    fun onPause() {
        assertNotNull(timer.cancel())
    }

    @Test
    fun checkOptionMenu() {
        val menuInflater = activityRule.activity.menuInflater
        assertNotNull(menuInflater)
    }

    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.gojek.githubtrending", appContext.packageName)
    }

    @Test
    fun isRefreshTrendingDisplayed() {
        onView(withId(R.id.srlTrending)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun itemTrendingCheck() {
        onView(withId(R.id.rvTrending)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
        onData(CoreMatchers.`is`(CoreMatchers.instanceOf(Trending::class.java)))
    }

    @Test
    fun itemTrendingClick() {
        onView(withId(R.id.rvTrending)).perform(
            RecyclerViewActions.actionOnItemAtPosition<TrendingAdapter.BindingHolder>(
                0,
                ViewActions.click()
            )
        )
    }
}