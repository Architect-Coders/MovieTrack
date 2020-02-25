package com.afrasilv.movietrack

import android.app.Application
import android.content.Intent
import android.os.SystemClock
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.jakewharton.espresso.OkHttp3IdlingResource
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.time.ExperimentalTime

@RunWith(AndroidJUnit4::class)
class UiTest {

    lateinit var server : MockWebServer

    @ExperimentalTime
    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java, false, false)

    @ExperimentalTime
    @get:Rule
    val grantPermissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(
            "android.permission.ACCESS_COARSE_LOCATION"
        )

    @ExperimentalTime
    @Before
    @Throws(Exception::class)
    fun setup() {
        val instrumentation= InstrumentationRegistry.getInstrumentation()
        val app = instrumentation.targetContext.applicationContext as Application
        val uiTestMovieTrackApp = UiTestMovieTrackApp()
        uiTestMovieTrackApp.createComponent(app)
        val component = uiTestMovieTrackApp.uiTestComponent

        server = component.mockWebServer

        val resource = OkHttp3IdlingResource.create("OkHttp", component.retrofitAPI.okHttpClient)
        IdlingRegistry.getInstance().register(resource)

        val intent = Intent(instrumentation.targetContext, MainActivity::class.java)

        activityTestRule.launchActivity(intent)
    }

    @After
    fun tearDown(){
        server.close()
    }

    @ExperimentalTime
    @Test
    fun testShowLoading() {
        onView(withId(R.id.home_progress_bar))
            .check(matches(isDisplayed()))
    }

    @ExperimentalTime
    @Test
    fun checkIfHasElementsInView() {
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(popularMovies)
        )

        SystemClock.sleep(1000)

        onView(withId(R.id.home_list)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )

        onView(withId(R.id.detailsTitleToolbar))
            .check(matches(hasDescendant(withText("Sonic the Hedgehog"))))

    }
}