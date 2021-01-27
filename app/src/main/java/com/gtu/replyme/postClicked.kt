package com.gtu.replyme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.android.synthetic.main.activity_post_clicked.*
import kotlinx.android.synthetic.main.recycler_view_row.*

class postClicked : AppCompatActivity() {
    var postId = ""
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
        soru.text=Question.toString()

    }
    fun reply(view: View)
    {
        val intent = Intent(applicationContext,AnswersActivity::class.java)
        intent.putExtra("postId",postId)
        startActivity(intent)
    }
    fun showAnswer(view: View)
    {
        val intent = Intent(applicationContext,showAnswer()::class.java)
        intent.putExtra("postId",postId)
        startActivity(intent)
    }

    fun openUser(view : View)
    {
        /*val intent = Intent(applicationContext,showAnswer()::class.java)
        intent.putExtra("postId",postId)
        startActivity(intent)*/
    }

}