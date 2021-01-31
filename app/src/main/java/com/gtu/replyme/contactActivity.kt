package com.gtu.replyme

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_contact.*
import kotlinx.android.synthetic.main.activity_contact_setting.*
import kotlinx.android.synthetic.main.activity_edit_nick_name.*
import kotlinx.android.synthetic.main.activity_upload.*
import java.lang.Exception
import java.util.*

class contactActivity : AppCompatActivity() {


    private lateinit var docId : String
    private lateinit var db : FirebaseFirestore
    private lateinit var auth : FirebaseAuth
    private lateinit var userId : String
    var facebook =""
    var github =""
    var linkedin=""
    var userEmail =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        auth  = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        userId = auth.uid.toString()






        //  textView3
        db.collection("Users").document(userId).collection("UsersData").addSnapshotListener { snapshot, exception -> //tüm sorular için
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
                        docId=document.id;
                        facebook = document.get("facebook") as String
                        github = document.get("github") as String
                        linkedin = document.get("linkedin") as String
                        userEmail = document.get("userEmail") as String

                        textView60.text=facebook
                        textView61.text=github
                        textView62.text=linkedin
                        textView63.text=userEmail

                        // val newNickName = editTextTextPersonName2.text.toString()


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





