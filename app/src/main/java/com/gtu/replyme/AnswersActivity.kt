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
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_answers.*
import kotlinx.android.synthetic.main.activity_upload.*
import java.lang.Exception
import java.util.*

class AnswersActivity : AppCompatActivity() {
    var postId = ""
    var selectedPicture: Uri? = null
    var answered = false
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answers)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        userId = auth.uid.toString()
        var intent = intent
        postId = intent.getStringExtra("postId")


    }

    fun fotose(View: View) { //fotoğraf seçme butonuna tıklandı

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )

        } else {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 2)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) //izni almışım zaten
            {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, 2)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {

            selectedPicture = data.data //secilen resmin adresi
            println(selectedPicture.toString())

            try {
                if (selectedPicture != null) {
                    if (Build.VERSION.SDK_INT >= 28) {
                        val source = ImageDecoder.createSource(contentResolver, selectedPicture!!)
                        val bitmap = ImageDecoder.decodeBitmap(source)
                        answerview.setImageBitmap(bitmap)
                    } else {
                        val bitmap =
                            MediaStore.Images.Media.getBitmap(this.contentResolver, selectedPicture)
                        answerview.setImageBitmap(bitmap)
                    }

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun answerButton(View: View) {//cevapmalambutonuna tıklandı

        val uuid = UUID.randomUUID()
        val imageName = "$uuid.jpg"

        answered = true

        val storage = FirebaseStorage.getInstance()
        val reference = storage.reference
        val imagesReference = reference.child("images").child(imageName)

        if (selectedPicture != null) { //fotoğraf seçildiyse
            imagesReference.putFile(selectedPicture!!).addOnSuccessListener { taskSnapshot ->
                val uploadedPictureReference =
                    FirebaseStorage.getInstance().reference.child("images").child(imageName)
                uploadedPictureReference.downloadUrl.addOnSuccessListener { uri ->
                    val downloadUrl = uri.toString()
                    println(downloadUrl)

                    val postMap = hashMapOf<String, Any>()

                    //val postMap2 = hashMapOf<String,Any>()

//postun kim tarafından atıldığını anlamak için veriyi çekiyoruz
                    db.collection("Posts").document(postId).addSnapshotListener(){ snapshot, exception -> //tüm sorular için
                        //   db.collection("Users").document(userId).collection("Posts").addSnapshotListener { snapshot, exception -> //kendi soruları için
                        if(exception !=null)
                        {
                            Toast.makeText(applicationContext,exception.localizedMessage.toString(),Toast.LENGTH_LONG).show()
                        }

                        else
                        {
                            val postUserId= snapshot?.get("postUserId") as String


                            postMap.put("downloadUrl", downloadUrl)
                            postMap.put("userEmail", auth.currentUser!!.email.toString())
                            postMap.put("Questions", answertext.text.toString())
                            postMap.put("date", com.google.firebase.Timestamp.now())
                            postMap.put("lock","locked")
                            postMap.put("postId",postId)
                            postMap.put("postUserId",postUserId)

                            val postMap2 = hashMapOf<String, Any>()
                            postMap2.put("postId",postId)
                            db.collection("Posts").document(postId).collection("Answer").add(postMap)
                                .addOnCompleteListener { task: Task<DocumentReference> ->
                                    if (task.isComplete && task.isSuccessful) {
                                        //  db.collection("testUser").
                                     /*   db.collection("Users").document(userId).collection("userAnswers")
                                            .add(postMap)*/
                                        //cevaba ait tüm verileri kaydediyoruz
                                        db.collection("Users").document(userId).collection("userAnswers")
                                            .add(postMap2)
                                        finish()

                                    }

                                }.addOnFailureListener { exception ->
                                    Toast.makeText(
                                        applicationContext,
                                        exception.localizedMessage.toString(),
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                        }
                    }


                    //  val exampleMap = hashMapOf<String,Ant>("downloadUrl"to downloadUrl)
                    //db.collection("Posts").add

                }
            }
        }
        if (selectedPicture == null) { //aynı işlemler fotoğraf seçilmeme koşulu için tekrarlanıyor



                    val postMap = hashMapOf<String, Any>()

                    //val postMap2 = hashMapOf<String,Any>()


                    db.collection("Posts").document(postId).addSnapshotListener(){ snapshot, exception -> //tüm sorular için
                        //   db.collection("Users").document(userId).collection("Posts").addSnapshotListener { snapshot, exception -> //kendi soruları için
                        if(exception !=null)
                        {
                            Toast.makeText(applicationContext,exception.localizedMessage.toString(),Toast.LENGTH_LONG).show()
                        }

                        else
                        {
                            val postUserId= snapshot?.get("postUserId") as String

                            //defould fotoğraf yok resmi
                            postMap.put("downloadUrl", "https://firebasestorage.googleapis.com/v0/b/replyme-35b58.appspot.com/o/images%2Frrrrrrrrrrrrrs%C4%B1.JPG?alt=media&token=69307ea6-c6cd-4c56-aebd-1a606df4954c")

                            postMap.put("userEmail", auth.currentUser!!.email.toString())
                            postMap.put("Questions", answertext.text.toString())
                            postMap.put("date", com.google.firebase.Timestamp.now())

                            postMap.put("lock","locked")
                            postMap.put("postId",postId)
                            postMap.put("postUserId",postUserId)
                            db.collection("Posts").document(postId).collection("Answer").add(postMap)
                                .addOnCompleteListener { task: Task<DocumentReference> ->
                                    if (task.isComplete && task.isSuccessful) {
                                        //  db.collection("testUser").
                                        db.collection("Users").document(userId).collection("userAnswers")
                                            .add(postMap)

                                        finish()

                                    }

                                }.addOnFailureListener { exception ->
                                    Toast.makeText(
                                        applicationContext,
                                        exception.localizedMessage.toString(),
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                        }
                    }


                    //  val exampleMap = hashMapOf<String,Ant>("downloadUrl"to downloadUrl)
                    //db.collection("Posts").add






        }

    }
}





