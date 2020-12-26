package com.gtu.replyme

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.EmailAuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.activity_log_in.*
import kotlinx.android.synthetic.main.activity_upload.*
import java.lang.Exception
import java.util.*

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        auth = FirebaseAuth.getInstance()
    }

    fun changePassword (view: View) {
        if(currentpassword.text.isNotEmpty()&&newpassword.text.isNotEmpty()&&newpassword2.text.isNotEmpty())
        {
            if(newpassword.text.toString().equals(newpassword2.text.toString()))
            {
                val user  = auth.currentUser
                val credential = EmailAuthProvider.getCredential(user?.email!!,currentpassword.text.toString())

                user?.reauthenticate(credential)?.addOnCompleteListener{
                    if(it.isSuccessful)
                    {
                     // Toast.makeText(this, "girdi1", Toast.LENGTH_SHORT).show()
                        user?.updatePassword(newpassword.text.toString())?.addOnCompleteListener{task->
                            if(task.isSuccessful){
                                Toast.makeText(this, "\n" + "Password Changed Successfully", Toast.LENGTH_SHORT).show()
                                auth.signOut()
                                val intent = Intent(applicationContext,MainActivity::class.java)
                                startActivity(intent)
                            }
                        }
                    }
                    else
                    {
                        Toast.makeText(this, "\n" + "Password Change Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else
            {
                Toast.makeText(this, "Passwords are not match!", Toast.LENGTH_SHORT).show()
            }
        }
        else
        {
            Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show()
        }

    }

}