package com.gtu.replyme;

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule

import com.google.firebase.auth.FirebaseAuth

import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock


@RunWith(AndroidJUnit4::class)
public class postClickedTest {

    @JvmField
    @Rule

    val rule:ActivityTestRule<postClicked> = ActivityTestRule(postClicked::class.java)

    private lateinit var auth :FirebaseAuth

    @Before

    fun setUp() {
        auth = FirebaseAuth.getInstance()
        rule.activity.postId = rule.activity.intent.getStringExtra("postId")

    }
    @Test
    fun user_can_reply_a_question(){
        onView(withId(R.id.button3)).perform(click())
        assertEquals(true,rule.activity.isclicked)

    }

    @Test
    fun user_can_see_answers_of_question(){
        onView(withId(R.id.button5)).perform(click())
        assertEquals(true,rule.activity.isclicked)
    }

}