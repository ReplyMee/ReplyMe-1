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
import kotlinx.android.synthetic.main.activity_upload.*
import java.lang.Exception
import java.util.*

class UploadActivity : AppCompatActivity() {

    var selectedPicture : Uri?=null
    private lateinit var db : FirebaseFirestore
    private lateinit var auth : FirebaseAuth

    private lateinit var userId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        auth  = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        userId = auth.uid.toString()
    }

    fun questionview(View: View)
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
        if(requestCode == 2 && resultCode==Activity.RESULT_OK && data != null)
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
                        imageView5.setImageBitmap(bitmap)
                    }
                    else
                    {
                        val  bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,selectedPicture)
                        imageView5.setImageBitmap(bitmap)
                    }

                }
            }
            catch(e:Exception)
            {
                e.printStackTrace()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun uploadbutton(View: View)
    {

        val uuid = UUID.randomUUID()
        val imageName = "$uuid.jpg"

        val storage=FirebaseStorage.getInstance()
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

                    postMap.put("userEmail",auth.currentUser!!.email.toString())
                    postMap.put("Questions",questiontext.text.toString())
                    postMap.put("date",com.google.firebase.Timestamp.now())

                    db.collection("Posts").add(postMap).addOnCompleteListener{task: Task<DocumentReference> ->
                        if (task.isComplete && task.isSuccessful)
                        {
                          //  db.collection("testUser").
                           db.collection("Users").document(userId).collection("Posts").add(postMap)

                            finish()

                        }

                    }.addOnFailureListener{exception ->  Toast.makeText(applicationContext, exception.localizedMessage.toString(),Toast.LENGTH_LONG).show()}
                    //  val exampleMap = hashMapOf<String,Ant>("downloadUrl"to downloadUrl)
                    //db.collection("Posts").add

                }
            }
        }
        if(selectedPicture==null)
        {






                    val postMap = hashMapOf<String,Any>()

                    postMap.put("downloadUrl","https://firebasestorage.googleapis.com/v0/b/replyme-35b58.appspot.com/o/images%2Faf3a4b69-6386-400c-90aa-6595b228f041.jpg?alt=media&token=d5d9e82f-bd9f-4856-9e20-bad190b156ab")
                    postMap.put("userEmail",auth.currentUser!!.email.toString())
                    postMap.put("Questions",questiontext.text.toString())
                    postMap.put("date",com.google.firebase.Timestamp.now())

                    db.collection("Posts").add(postMap).addOnCompleteListener{task: Task<DocumentReference> ->
                        if (task.isComplete && task.isSuccessful)
                        {
                            db.collection("Users").document(userId).collection("Posts").add(postMap)
                            finish()

                        }

                    }.addOnFailureListener{exception ->  Toast.makeText(applicationContext, exception.localizedMessage.toString(),Toast.LENGTH_LONG).show()}
                    //  val exampleMap = hashMapOf<String,Ant>("downloadUrl"to downloadUrl)
                    //db.collection("Posts").add



        }




    }

}
