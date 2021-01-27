package com.gtu.replyme;

import android.content.Intent
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
public class AnswersActivityTest {

    @JvmField
    @Rule

    val rule:ActivityTestRule<AnswersActivity> = ActivityTestRule(AnswersActivity::class.java)

    private lateinit var auth :FirebaseAuth
    private lateinit var db: FirebaseFirestore

    var mintent = rule.activity.intent
    var mpostId = mintent.extras.toString()


    @Before

    fun setUp() {

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        rule.activity.userId = auth.uid.toString()
       // var mintent = rule.activity.intent
        rule.activity.postId = mintent.getStringExtra("postId")


    }

    @Test
    fun user_can_add_answer(){
        onView(withId(R.id.answertext)).perform(typeText("This is an answer"))
        onView(withId(R.id.button)).perform(click())
        assertEquals("C",true,rule.activity.answered)
    }

}