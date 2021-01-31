package com.gtu.replyme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_other_user_profil.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.UserName

class otherUserProfil : AppCompatActivity() {

    private lateinit var  auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore
    private lateinit var postUserId: String
    private lateinit var postId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_user_profil)
        postId =  intent.getStringExtra("postId")

        auth  = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

      //bir postta postu atan kişinin profili


          db.collection("Posts").document(postId).addSnapshotListener() { snapshot, exception -> //tüm sorular için
              //   db.collection("Users").document(userId).collection("Posts").addSnapshotListener { snapshot, exception -> //kendi soruları için
              if (exception != null) {
                  Toast.makeText(
                      applicationContext,
                      exception.localizedMessage.toString(),
                      Toast.LENGTH_LONG
                  ).show()
              } else {
                  postUserId = snapshot?.get("postUserId") as String



                  db.collection("Users").document(postUserId).collection("UsersData").addSnapshotListener { snapshot, exception -> //tüm sorular için
                      //   db.collection("Users").document(userId).collection("Posts").addSnapshotListener { snapshot, exception -> //kendi soruları için
                      if(exception !=null)
                      {
                          Toast.makeText(applicationContext,exception.localizedMessage.toString(),Toast.LENGTH_LONG).show()
                      }

                      else
                      {
                          if(snapshot != null&&!snapshot.isEmpty)
                          {

                              val documents= snapshot.documents
                              for (document in documents)
                              {
                                  val docId=document.id;

                                  //    val downloadUrl = document.get("downloadUrl") as String
                                  //db.collection("Users").document(userId).collection("UsersData").document(docId).update("ProfilPhoto","assssssssssss")
                                  val urlPP :String  = document.get("profilPhoto") as String
                                  Picasso.get().load(urlPP).into(ProfilePhotoOther)
                                //  val userNickName = document.get("userNickName") as String
                                  UserNameOther.text=document.get("userNickName") as String
                              }


                          }
                          else
                          {
                              println("elseeeee")
                          }


                      }
                  }




              }

          }
        /*  println(postUserId)
        println(postUserId)
        println(postUserId)*/

    }

    fun otherQuestions(view : View)
    {
        val intent = Intent(applicationContext,otherQuestions()::class.java)
        intent.putExtra("postId",postId)
        startActivity(intent)
    }
    fun otherAnswers(view : View)
    {
        val intent = Intent(applicationContext,otherAnswer()::class.java)
        intent.putExtra("postId",postId)
        startActivity(intent)
    }
}