package com.gtu.replyme

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_re_password.*
import com.google.firebase.auth.FirebaseAuth

class rePassword : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    var mailSended = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_re_password)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()



    }

    fun sendEmail(view: View) {
        val email = Email_Send.text.toString()

        mailSended = true;

        if(email.isEmpty()) {
            return
        }

        auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if(task.isSuccessful) {
                Toast.makeText( this,"Email Sent", Toast.LENGTH_SHORT).show()
                finish()
            }
            else {
                Toast.makeText( this,"Email Not Sent", Toast.LENGTH_SHORT).show()
            }

        }
    }
}