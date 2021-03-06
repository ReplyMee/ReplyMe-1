package com.gtu.replyme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_log_in.*
import kotlin.system.exitProcess

class LoginActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth


    var loginSuccessFull = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if(currentUser!=null)
        {

            val intent = Intent(applicationContext, FeedActivity::class.java)
            intent.putExtra("catagory","null")
            startActivity(intent)
            finish()
        }
    }

    fun signInClick(view : View)
    {

        val email = emaill.text.toString()
        val password = Password.text.toString()
         LogIn(email,password)
    }
    fun LogIn(email : String, password: String ){

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {task ->
            if(task.isSuccessful)
            {
                Toast.makeText(applicationContext,"Welcome: ${auth.currentUser?.email.toString()}",
                    Toast.LENGTH_LONG).show()
                val intent = Intent(applicationContext,FeedActivity::class.java)
                intent.putExtra("catagory","null")
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener { exception->
            Toast.makeText(applicationContext,exception.localizedMessage.toString(), Toast.LENGTH_LONG).show()
        }
    }

    fun signUpClick(view: View)
    {
        val intent = Intent(applicationContext,sign_up::class.java)
        startActivity(intent)
        finish()
    }

    fun forgotPassword(view: View)
    {
        val intent = Intent(applicationContext, rePassword::class.java)
        startActivity(intent)

    }

    fun logoutfun(){
        exitProcess(-1)
    }

}