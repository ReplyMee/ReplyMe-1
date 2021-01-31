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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_edit_photo.*
import kotlinx.android.synthetic.main.activity_upload.*
import java.lang.Exception
import java.util.*

class EditPhoto : AppCompatActivity() {

    var selectedPicture : Uri?=null
    private lateinit var docId : String
    private lateinit var db : FirebaseFirestore
    private lateinit var auth : FirebaseAuth
    private lateinit var userId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_photo)
        auth  = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        userId = auth.uid.toString()
    }

    fun editPhoto(View: View)
    {

        if(ContextCompat.checkSelfPermission(this , Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)

        }
        else
        {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent,2)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == 1)
        {
            if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) //izni almışım zaten
            {
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent,2)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 2 && resultCode== Activity.RESULT_OK && data != null)
        {

            selectedPicture = data.data //secilen resmin adresi
            println(selectedPicture.toString())

            try {
                if(selectedPicture!=null)
                {
                    if(Build.VERSION.SDK_INT >=28)
                    {
                        val source = ImageDecoder.createSource(contentResolver,selectedPicture!!)
                        val bitmap = ImageDecoder.decodeBitmap(source)
                        profilePhotoNew.setImageBitmap(bitmap)
                    }
                    else
                    {
                        val  bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,selectedPicture)
                        profilePhotoNew.setImageBitmap(bitmap)
                    }

                }
            }
            catch(e: Exception)
            {
                e.printStackTrace()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    fun Ok(View: View)
    {


        val uuid = UUID.randomUUID()
        val imageName = "$uuid.jpg"

        val storage= FirebaseStorage.getInstance()
        val reference = storage.reference
        val imagesReference = reference.child("images").child(imageName)

        if(selectedPicture!=null)
        {
            imagesReference.putFile(selectedPicture!!).addOnSuccessListener { taskSnapshot ->
                val uploadedPictureReference = FirebaseStorage.getInstance().reference.child("images").child(imageName)
                uploadedPictureReference.downloadUrl.addOnSuccessListener { uri ->
                    val downloadUrl = uri.toString()
                    println(downloadUrl)

                    val postMap = hashMapOf<String,Any>()

                    //val postMap2 = hashMapOf<String,Any>()


                    postMap.put("downloadUrl",downloadUrl)



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
                                //    val downloadUrl = document.get("downloadUrl") as String
                                    db.collection("Users").document(userId).collection("UsersData").document(docId).update("profilPhoto",downloadUrl)

                                }


                            }
                            else
                            {
                                println("elseeeee")
                            }


                        }
                    }





















                  // db.collection("Users").document(userId).collection("ProfilPhoto").document(docId).update("test","ana")


                        /*    //  db.collection("testUser").
                            db.collection("Users").document(userId).collection("Posts").add(postMap)
*/








                    //  val exampleMap = hashMapOf<String,Ant>("downloadUrl"to downloadUrl)
                    //db.collection("Posts").add

                }
            }
        }
        if(selectedPicture==null)
        {
            //toastmasage foto seç yaz
        }



        finish()
    }

}

