package com.gtu.replyme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
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

    }
    fun editUserContact(view: View) {

    }
    fun logOut(view: View) {
        finishAffinity()
    }
}