package com.gtu.replyme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Categorys : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categorys)
    }

    /*

            val intent = Intent(applicationContext,postClicked::class.java)
        intent.putExtra("postId",uidImage)
        intent.putExtra("Email",Email)
        intent.putExtra("Question",Question)
        intent.putExtra("Image",Image)
        catagory
        startActivity(intent)*/

    //feed activitye katagori parametresi gönderiliyor
    fun music(view: View)
    {
        val intent = Intent(applicationContext, FeedActivity::class.java)
        intent.putExtra("catagory","Music")
        startActivity(intent)
    }
    fun auto(view: View)
    {
        val intent = Intent(applicationContext, FeedActivity::class.java)
        intent.putExtra("catagory","Auto")
        startActivity(intent)
    }

    fun computer(view: View)
    {
        val intent = Intent(applicationContext, FeedActivity::class.java)
        intent.putExtra("catagory","Computer")
        startActivity(intent)
    }
    fun life(view: View)
    {
        val intent = Intent(applicationContext, FeedActivity::class.java)
        intent.putExtra("catagory","Life")
        startActivity(intent)
    }

    fun code(view: View)
    {
        val intent = Intent(applicationContext, FeedActivity::class.java)
        intent.putExtra("catagory","Code")
        startActivity(intent)
    }
    fun law(view: View)
    {
        val intent = Intent(applicationContext, FeedActivity::class.java)
        intent.putExtra("catagory","Law")
        startActivity(intent)
    }
    fun health(view: View)
    {
        val intent = Intent(applicationContext, FeedActivity::class.java)
        intent.putExtra("catagory","Health")
        startActivity(intent)
    }
    fun travel(view: View)
    {
        val intent = Intent(applicationContext, FeedActivity::class.java)
        intent.putExtra("catagory","Travel")
        startActivity(intent)
    }
    fun ayt(view: View)
    {
        val intent = Intent(applicationContext, FeedActivity::class.java)
        intent.putExtra("catagory","Ayt")
        startActivity(intent)
    }
    fun tyt(view: View)
    {
        val intent = Intent(applicationContext, FeedActivity::class.java)
        intent.putExtra("catagory","Tyt")
        startActivity(intent)
    }

     fun art(view: View)
    {
        val intent = Intent(applicationContext, FeedActivity::class.java)
        intent.putExtra("catagory","Aome")
        startActivity(intent)
    }

    fun home(view: View)
    {
        val intent = Intent(applicationContext, FeedActivity::class.java)
        intent.putExtra("catagory","Home")
        startActivity(intent)
    }
    fun sport(view: View)
    {
        val intent = Intent(applicationContext, FeedActivity::class.java)
        intent.putExtra("catagory","Sport")
        startActivity(intent)
    }

    fun gardening(view: View)
    {
        val intent = Intent(applicationContext, FeedActivity::class.java)
        intent.putExtra("catagory","Gardening")
        startActivity(intent)
    }
    fun pet(view: View)
    {
        val intent = Intent(applicationContext, FeedActivity::class.java)
        intent.putExtra("catagory","Pet")
        startActivity(intent)
    }

    fun others(view: View)
    {
        val intent = Intent(applicationContext, FeedActivity::class.java)
        intent.putExtra("catagory","Others")
        startActivity(intent)
    }


}