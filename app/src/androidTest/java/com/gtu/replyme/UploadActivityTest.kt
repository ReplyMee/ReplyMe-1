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
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UploadActivityTest {

    @JvmField
    @Rule

    val rule: ActivityTestRule<UploadActivity> = ActivityTestRule(UploadActivity::class.java)
    private lateinit var auth : FirebaseAuth
 //   private lateinit var mUploadActivity : UploadActivity

    @Before
    fun setUp() {
        auth = FirebaseAuth.getInstance()
       // mUploadActivity = UploadActivity()

    }

    @Test
    fun user_can_choose_image() {
        onView(withId(R.id.questionPicture)).perform(click())
    }



    @Test
    fun user_can_add_question_without_a_picture() {
        onView(withId(R.id.questiontext)).perform(typeText("Question Added Without any picture"))
        onView(withId(R.id.button)).perform(closeSoftKeyboard())
        onView(withId(R.id.button)).perform(click())
        assertNotEquals("user cannot upload a picture", false , rule.activity.isupload)
    }


}