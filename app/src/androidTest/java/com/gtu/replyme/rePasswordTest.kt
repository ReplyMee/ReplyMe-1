package com.gtu.replyme

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


@RunWith(AndroidJUnit4::class)
class rePasswordTest {

    @JvmField
    @Rule

    val rule: ActivityTestRule<rePassword> = ActivityTestRule(rePassword::class.java)
    private lateinit var auth : FirebaseAuth

    @Before
    fun setUp() {
        auth = FirebaseAuth.getInstance()
    }

    @Test
    fun user_can_change_password() {

        onView(withId(R.id.Email_Send)).perform(typeText("testmail@gmail.com"))
        onView(withId(R.id.sendEmail)).perform(click())
        assertEquals("Mail can not send",true,rule.activity.mailSended)
    }
}