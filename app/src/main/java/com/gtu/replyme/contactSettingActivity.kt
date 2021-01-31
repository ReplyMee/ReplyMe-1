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
import kotlinx.android.synthetic.main.activity_contact_setting.*
import kotlinx.android.synthetic.main.activity_edit_nick_name.*
import kotlinx.android.synthetic.main.activity_upload.*
import java.lang.Exception
import java.util.*

class contactSettingActivity : AppCompatActivity() {


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
        setContentView(R.layout.activity_contact_setting)

        auth  = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        userId = auth.uid.toString()
    }


    fun Guncelle(View: View)
    {

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

                       // val newNickName = editTextTextPersonName2.text.toString()

                        //if koşullarında eski veri ile yeni veri kıyaslanıyor farklı ise yeni veri güncelleniyor
                        if(facebook!=editTextTextPersonName1.text.toString())
                        {
                            db.collection("Users").document(userId).collection("UsersData").document(docId).update("facebook",editTextTextPersonName1.text.toString())
                        }
                        if(github!=editTextTextPersonName31.text.toString())
                        {
                            db.collection("Users").document(userId).collection("UsersData").document(docId).update("github",editTextTextPersonName31.text.toString())

                        }
                        if(linkedin!=editTextTextPersonName41.text.toString())
                        {
                            db.collection("Users").document(userId).collection("UsersData").document(docId).update("linkedin",editTextTextPersonName41.text.toString())

                        }
                        if(userEmail!=editTextTextPersonName51.text.toString())
                        {
                            db.collection("Users").document(userId).collection("UsersData").document(docId).update("userEmail",editTextTextPersonName51.text.toString())

                        }


                    }


                }
                else
                {
                    println("elseeeee")
                }


            }
        }
        finish()
    }
}





