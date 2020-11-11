package com.dicoding.ohmymovies.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.startsWith
import org.hamcrest.TypeSafeMatcher

object EspressoExt{
    fun matchToolbarTitle(title : String) : ViewAssertion {
        return matches(withToolbarTitle(startsWith(title)))
    }

    private fun withToolbarTitle(textMatcher : Matcher<String>) : Matcher<View> {
        return object : BoundedMatcher<View, Toolbar>(Toolbar::class.java){
            override fun describeTo(description: Description?) {}

            override fun matchesSafely(item: Toolbar?): Boolean {
                if (item == null) return false
                return textMatcher.matches(item.title)
            }

        }
    }

    fun withDrawable(@DrawableRes id: Int) = object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("Drawable not found")
        }

        override fun matchesSafely(view: View): Boolean {
            val context = view.context
            val expectedBitmap = context.getDrawable(id)?.toBitmap()

            return view is ImageView && view.drawable.toBitmap().sameAs(expectedBitmap)
        }
    }
}