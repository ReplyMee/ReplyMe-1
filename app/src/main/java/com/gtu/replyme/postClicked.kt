package com.gtu.replyme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_post_clicked.*

class postClicked : AppCompatActivity() {
    var postId = ""
    var isclicked = false
    var otherUserId =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_clicked)

        var intent = intent
        postId =  intent.getStringExtra("postId")
        val eMail = intent.getStringExtra("Email")
        val Question = intent.getStringExtra("Question")
         val ımageUrl = intent.getStringExtra("Image")
        Picasso.get().load(ımageUrl).into(imageView2)
        email.text=eMail.toString()
        questionanswerclicked.text=Question.toString()

    }
    fun reply(view: View)
    {
        isclicked = true
        val intent = Intent(applicationContext,AnswersActivity::class.java)
        intent.putExtra("postId",postId)
        startActivity(intent)
    }
    fun showAnswer(view: View)
    {
        isclicked = true
        val intent = Intent(applicationContext,showAnswer()::class.java)
        intent.putExtra("postId",postId)
        startActivity(intent)
    }

    fun openUser(view : View)
    {

        val intent = Intent(applicationContext,otherUserProfil()::class.java)
        intent.putExtra("postId",postId)
        startActivity(intent)
    }

}