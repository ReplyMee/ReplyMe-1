package com.gtu.replyme

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.firestore.util.Assert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*




import org.junit.runner.RunWith



@RunWith(AndroidJUnit4::class)
class LoginTest {

    @JvmField
    @Rule

    val rule: ActivityTestRule<LoginActivity> = ActivityTestRule(LoginActivity::class.java)
    private lateinit var auth : FirebaseAuth


    //val l1 = LoginActivity()
    /*
*Login Activity xml dosyasını kontol eden testler:
*/

    @Before
  /*  fun logout(){
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if(currentUser!=null)
        {
            rule.activity.logoutfun()
        }
    }*/




   /* @After
    fun deneme(){
        println("BİTTİ")
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        assertEquals("PATLADI", null , currentUser)
    }*/
   //
    @Test
   fun user_can_switch_signUP(){

       onView(withId(R.id.SignupPage)).perform(click())
   }

    @Test
    fun user_can_edit_texts(){
        onView(withId(R.id.emaill)).perform(typeText("muhammed2132@gmail.com"))
        onView(withId(R.id.Password)).perform(typeText("123456"))
    }

    @Test
    fun member_can_login() {
        println("Message")
        onView(withId(R.id.emaill)).perform(typeText("muhammed212@gmail.com"))
        onView(withId(R.id.Password)).perform(typeText("123456"), closeSoftKeyboard())
        onView(withId(R.id.login_button)).perform(click())
      //assert

        assert(rule.activity.loginSuccessFull.equals(false))
      // assert(auth.currentUser?.email.toString() == "muhammed2132@gmail.com")
      //  assertNotNull

    }




   // @Test
    /*fun `empty mail adress returns false`(){

        val result = onView(LoginActivity).check()

    }*/


    /*//private  LoginActivity login();
    /**
     * Set up the environment for testing
     */

    @Before
    fun setup (){
       // LoginActivity login = new LoginActivity();
    }
        /*
        * Test for
         */
    @Test
    fun loginwithcorrectloginandpassword() {

        //given
        val objectUnderTest = LoginActivity();

       /* val correctLogin = "mstfsnol@gmail.com"
        val `123456`
        val correctPassword = `123456`*/

        //when
        val result = objectUnderTest.signInClick()




        //then
       // result.
    }*/
}