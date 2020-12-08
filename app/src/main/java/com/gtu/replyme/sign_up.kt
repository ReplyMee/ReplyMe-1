package com.gtu.replyme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_upload.*

class sign_up : AppCompatActivity() {
    private lateinit var db : FirebaseFirestore
    private lateinit var auth : FirebaseAuth
    private lateinit var userId : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        db = FirebaseFirestore.getInstance()
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
                userId = auth.uid.toString()
                val postMap = hashMapOf<String,Any>()

                postMap.put("userEmail",auth.currentUser!!.email.toString())
                postMap.put("userNickName",editTextTextPersonName2.text.toString())


                db.collection("Users").document(userId).collection("UsersData").add(postMap)

              //  db.collection("testUserr").document(userId).collection("UsersData").document(editTextTextPersonName2.text.toString())



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