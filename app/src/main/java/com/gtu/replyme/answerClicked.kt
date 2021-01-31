package com.gtu.replyme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_answer_clicked.*
import kotlinx.android.synthetic.main.activity_post_clicked.imageView2

class answerClicked : AppCompatActivity() {
    var postId = ""
    var isclicked = false
    var otherUserId =""
    var postUserId=""

    private lateinit var  auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore


//onaylanmış cevabı gösterir

    private lateinit var userId : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer_clicked)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        userId = auth.uid.toString()

        var intent = intent

        postId =  intent.getStringExtra("postId")
        val eMail = intent.getStringExtra("Email")
        val Question = intent.getStringExtra("Question")
        val ımageUrl = intent.getStringExtra("Image")
        val lock=intent.getStringExtra("lock")
        var postUserId = intent.getStringExtra("postUserId")
       val questionId = intent.getStringExtra("questionId")
        val postId = intent.getStringExtra("postId")
        Picasso.get().load(ımageUrl).into(imageView2)
        emailAnswerClicked.text=eMail
      //  email.text="eMail.toString()"
        questionanswerclickedd.text=Question
     //   editTextTextPersonName.text
      //  editTextTextPersonName3.text=
      /*  textView.text= questionId
        textView2.text=postId*/


    }

    fun openUser(view : View)// postta kişinin pp sine ya dakullanıcı ismine tıklandığı zaman o kişinin profilini açar
    {

        val intent = Intent(applicationContext,otherUserProfil()::class.java)
        intent.putExtra("postId",postId)
        startActivity(intent)
    }

}