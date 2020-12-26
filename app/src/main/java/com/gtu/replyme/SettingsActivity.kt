package com.gtu.replyme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth

class SettingsActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        auth  = FirebaseAuth.getInstance()
    }
    fun editUserName(view: View) {
        val intent = Intent(applicationContext,editNickName::class.java)
        startActivity(intent)
    }
    fun editUserPhoto(view: View) {
        val intent = Intent(applicationContext,EditPhoto::class.java)
        startActivity(intent)
    }
    fun changePassword(view: View) {
        val intent = Intent(applicationContext,ChangePasswordActivity::class.java)
        startActivity(intent)
    }
    fun editUserContact(view: View) {

    }
    fun logOut(view: View) {



        auth.signOut()
        val intent = Intent(applicationContext,MainActivity::class.java)
        startActivity(intent)

    }
}