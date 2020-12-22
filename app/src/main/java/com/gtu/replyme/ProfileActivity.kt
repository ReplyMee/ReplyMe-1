package com.gtu.replyme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
    }
    fun yourQuestions(view: View) {
        val intent = Intent(applicationContext,QuestionsActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun yourAnswers(view: View) {
        val intent = Intent(applicationContext,AnswersActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun settings(view: View) {
        val intent = Intent(applicationContext,SettingsActivity::class.java)
        startActivity(intent)
        finish()
    }
}

