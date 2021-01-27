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
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_upload.*
import java.lang.Exception
import java.lang.reflect.Field
import java.util.*
import kotlin.collections.ArrayList

class UploadActivity : AppCompatActivity() {

    var selectedPicture : Uri?=null
    private lateinit var db : FirebaseFirestore
    private lateinit var auth : FirebaseAuth
    private lateinit var selectedCatgo : String
    private lateinit var userId : String
    val list : MutableList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        auth  = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        userId = auth.uid.toString()
        list.add(0,"Select a category")
        list.add(1,"Gardening")
        list.add(2,"Pet")
        list.add(3,"Music")
        list.add(4,"Computer")
        list.add(5,"Life")
        list.add(6,"Code")
        list.add(7,"Law")
        list.add(8,"Health")
        list.add(9,"Travel")
        list.add(10,"Ayt")
        list.add(11,"Tyt")
        list.add(12,"Home")
        list.add(13,"Sport")
        list.add(14,"Other")
        val adapter:ArrayAdapter<String> = ArrayAdapter( this,R.layout.support_simple_spinner_dropdown_item,list)
        spnTest.adapter=adapter

        spnTest.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
               val item= list[position]
                selectedCatgo=item
                // Toast.makeText(applicationContext,"$item selected",Toast.LENGTH_SHORT).show()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}

        }

       // limitDropDownHeight(spnTest)
    }

    fun limitDropDownHeight(spnTest: Spinner) { //https://www.youtube.com/watch?v=NoZc2jWu0uA
        //calışmıyor
        val popup = Spinner::class.java.getDeclaredField("mPopup")

        popup.isAccessible = true

        val popupWindow = popup.get(spnTest) as ListPopupWindow
        popupWindow.height = (200 * resources.displayMetrics.density).toInt()
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

        if(selectedCatgo=="Select a category"||!questiontext.text.toString().isNotEmpty())
        {
            if(selectedCatgo=="Select a category")
                 Toast.makeText(applicationContext,"Select a category",Toast.LENGTH_SHORT).show()
            if (!questiontext.text.toString().isNotEmpty())
                Toast.makeText(applicationContext,"Please enter a question",Toast.LENGTH_SHORT).show()
        }

        else
        {
            val uuid = UUID.randomUUID()
            val uuidS = uuid.toString()
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


                        postMap.put("catagory",selectedCatgo)
                        postMap.put("downloadUrl",downloadUrl)

                        postMap.put("userEmail",auth.currentUser!!.email.toString())
                        postMap.put("Questions",questiontext.text.toString())
                        postMap.put("date",com.google.firebase.Timestamp.now())

                        db.collection("Posts").add(postMap).addOnCompleteListener{task: Task<DocumentReference> ->
                            if (task.isComplete && task.isSuccessful)
                            {
                                //  db.collection("testUser").
                                db.collection("Users").document(userId).collection("Posts").add(postMap)
                                db.collection("Catagorys").document("subCatagorys").collection(selectedCatgo).add(postMap)
                                finish()


                            }

                        }.addOnFailureListener{exception ->  Toast.makeText(applicationContext, exception.localizedMessage.toString(),Toast.LENGTH_LONG).show()}

                        //  db.collection("testUser").

                        //  val exampleMap = hashMapOf<String,Ant>("downloadUrl"to downloadUrl)

                        //db.collection("Posts").add

                    }
                }



            }

            if(selectedPicture==null)
            {

                val postMap = hashMapOf<String,Any>()

                postMap.put("downloadUrl","https://firebasestorage.googleapis.com/v0/b/replyme-35b58.appspot.com/o/images%2Frrrrrrrrrrrrrs%C4%B1.JPG?alt=media&token=69307ea6-c6cd-4c56-aebd-1a606df4954c")
                postMap.put("userEmail",auth.currentUser!!.email.toString())
                postMap.put("Questions",questiontext.text.toString())
                postMap.put("date",com.google.firebase.Timestamp.now())

                db.collection("Posts").add(postMap).addOnCompleteListener{task: Task<DocumentReference> ->
                    if (task.isComplete && task.isSuccessful)
                    {
                        db.collection("Users").document(userId).collection("Posts").add(postMap)
                        db.collection("Catagorys").document("subCatagorys").collection(selectedCatgo).add(postMap)

                        finish()

                    }

                }.addOnFailureListener{exception ->  Toast.makeText(applicationContext, exception.localizedMessage.toString(),Toast.LENGTH_LONG).show()}
                //  val exampleMap = hashMapOf<String,Ant>("downloadUrl"to downloadUrl)
                //db.collection("Posts").add



            }

        }




    }

}
