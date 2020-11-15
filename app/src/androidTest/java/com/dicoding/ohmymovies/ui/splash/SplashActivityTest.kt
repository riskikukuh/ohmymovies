package com.dicoding.ohmymovies.ui.splash

//import com.dicoding.ohmymovies.util.EspressoIdlingResource
import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.dicoding.ohmymovies.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class SplashActivityTest {

    /**
     * Memastikan title dan logo ada di layout
     */
    @Test
    fun assertTitleAndlogo(){
        lateinit var context : Context
        val scenario = ActivityScenario.launch(SplashActivity::class.java).onActivity {
            context = it.applicationContext
        }
        onView(withId(R.id.logo))
            .check(matches(withContentDescription("Logo OhMyMovies")))
        onView(withId(R.id.title))
            .check(matches(withText(context.getString(R.string.app_name))))
    }

}