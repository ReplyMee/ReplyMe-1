package com.gtu.replyme

import android.view.MenuItem
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_log_in.*
import org.junit.Test


@RunWith(AndroidJUnit4::class)
class FeedActivityTest {

    @JvmField
    @Rule
    val rule: ActivityTestRule<FeedActivity> = ActivityTestRule(FeedActivity::class.java)
    private lateinit var auth : FirebaseAuth

    @Before
    fun setUp() {

    }
    @Test
    fun user_can_logout(){

        rule.activity.logoutfun()
        auth = FirebaseAuth.getInstance()
        var userId = auth.uid
        assertNull("Logout Failed",userId)


    }
    @Test
    fun FeedActivity_Gets_Data(){

       // rule.activity.onOptionsItemSelected()
       // var expexted = super.onOptions
    }

    @After
    fun tearDown() {
    }
}