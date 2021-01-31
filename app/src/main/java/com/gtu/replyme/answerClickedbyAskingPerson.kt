package com.gtu.replyme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_answer_clickedby_asking_person.*
import kotlinx.android.synthetic.main.activity_edit_nick_name.*
import kotlinx.android.synthetic.main.activity_post_clicked.*
import kotlinx.android.synthetic.main.activity_post_clicked.email
import kotlinx.android.synthetic.main.activity_post_clicked.imageView2

class answerClickedbyAskingPerson : AppCompatActivity() {

    var postId = ""
    var isclicked = false
    var otherUserId =""
    var postUserId=""
    var questionId=""


    private lateinit var  auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore
    //private lateinit var postId: String
    private lateinit var userId : String
//soruyu soran kişi onaylanmamış cevabı bu aktrivityde görür
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer_clickedby_asking_person)





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

        postId = intent.getStringExtra("postId")
        questionId = intent.getStringExtra("questionId")

        Picasso.get().load(ımageUrl).into(imageView2)

        email.text=eMail.toString()
        questionanswerclickedbyaskingperson.text=Question





    }




    fun Confirm(View: View) //onaylama butonuyla firebase de locked olan yer unlocked' e çevirlir
    {
        db.collection("Posts").document(postId).collection("Answer").document(questionId).addSnapshotListener { snapshot, exception -> //tüm sorular için
            //   db.collection("Users").document(userId).collection("Posts").addSnapshotListener { snapshot, exception -> //kendi soruları için
            if(exception !=null)
            {
                Toast.makeText(applicationContext,exception.localizedMessage.toString(), Toast.LENGTH_LONG).show()
            }
            else
            {

              //  val documents= snapshot[""]
                        db.collection("Posts").document(postId).collection("Answer").document(questionId).update("lock","unlocked")

            }
        }
    }


}