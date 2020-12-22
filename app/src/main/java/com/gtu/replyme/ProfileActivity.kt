package com.gtu.replyme

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_log_in.*
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var  auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore
    private lateinit var userId : String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth  = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()


        userId = auth.uid.toString()
        val db = FirebaseFirestore.getInstance()



      //  db.collection("Users").document(userId).collection("UserData").addSnapshotListener

        // db.collection("Users").document(userId).collection("Posts").add(postMap)

        println(userId)

        db.collection("Users").document(userId).collection("UsersData").addSnapshotListener { snapshot, exception -> //tüm sorular için

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


                        val userNickName = document.get("userNickName") as String
                        UserName.text=userNickName
                    }


                }



            }
        }


       // db.collection("Users").document(userId).collection("ProfilPhoto").document()


        db.collection("Users").document(userId).collection("ProfilPhoto").addSnapshotListener { snapshot, exception ->

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

                        val urlPP = document.get("downloadUrl") as String
                        Picasso.get().load(urlPP).into(ProfilePhoto)
                    }
                }
            }
        }









        //db.collection("Posts").addSnapshotListener { snapshot, exception -> //tüm sorular için
        //    db.collection("Users").document(userId).collection("Posts").addSnapshotListener { snapshot, exception -> //kendi soruları için

            // UserName.text="test"
    }
    fun yourQuestions(view: View) {
        val intent = Intent(applicationContext,Myquestions::class.java)
        startActivity(intent)
       // finish()
    }
    fun yourAnswers(view: View) {
       /* val intent = Intent(applicationContext,AnswersActivity::class.java)
        startActivity(intent)*/
       // finish()
    }
    fun settings(view: View) {
        val intent = Intent(applicationContext,SettingsActivity::class.java)
        startActivity(intent)
     //   finish()
    }
}
//db.collection("Users").document(userId).collection("UsersData").add(postMap)










