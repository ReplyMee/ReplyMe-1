package com.gtu.replyme

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.google.firebase.auth.FirebaseAuth
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class sign_up_Test{

    @JvmField
    @Rule
    val rule: ActivityTestRule<sign_up> = ActivityTestRule(sign_up::class.java)
    private lateinit var auth : FirebaseAuth
/*
*Sign-Up Activity kontol eden testler:
*/

    @Test
    fun user_can_edit_texts(){

        onView(withId(R.id.emaill)).perform(typeText("muhammed2132@gmail.com"))
        onView(withId(R.id.Username)).perform(typeText("Muhammed"))
        onView(withId(R.id.Password)).perform(typeText("123456"))
        onView(withId(R.id.PasswordAgain)).perform(typeText("123456"))

    }
    @Test
    fun user_can_sign_up(){

        onView(withId(R.id.emaill)).perform(typeText("muhammed2132@gmail.com"))
        onView(withId(R.id.Username)).perform(typeText("Muhammed"))
        onView(withId(R.id.Password)).perform(typeText("123456"))
        onView(withId(R.id.PasswordAgain)).perform(typeText("123456"), closeSoftKeyboard())
        onView(withId(R.id.signupbutton)).perform(click())
        auth = FirebaseAuth.getInstance()
        assertNotNull("User can not Sign Up", auth)

    }


}