package com.gtu.replyme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

import kotlinx.android.synthetic.main.activity_sign_up.*

class sign_up : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()
    }
    fun singnUpClicked(view : View)
    {
        val email = editTextTextPersonName3.text.toString()
        val password = editTextTextPassword4.text.toString()
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {task ->
            if(task.isSuccessful)
            {
                val intent = Intent(applicationContext,FeedActivity::class.java)
                startActivity(intent)
                finish()
            }

        }.addOnFailureListener { exception ->
            if(exception !=null)
            {
                Toast.makeText(applicationContext,exception.localizedMessage.toString(), Toast.LENGTH_LONG).show()
            }
        }


    }
    fun backToSign(view: View)
    {
        val intent = Intent(applicationContext,LogInActivity::class.java)
        startActivity(intent)
        finish()
    }

}