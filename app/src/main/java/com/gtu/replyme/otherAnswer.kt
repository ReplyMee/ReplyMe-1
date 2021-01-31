package com.gtu.replyme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.android.synthetic.main.recycler_view_row.*

class otherAnswer : AppCompatActivity() , OnCarItemClickListner {

    private lateinit var  auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore
    private lateinit var postId: String

    private lateinit var userId : String
    var tttest = "0"
    var userEmailFromFB :ArrayList<String> = ArrayList()
    var userQuestionFromFB :ArrayList<String> = ArrayList()
    var userImageFromFB :ArrayList<String> = ArrayList() //url adres
    var postIdFromFB : ArrayList<String> = ArrayList()

    var adapter : FeedRecyclerAdapter?=null

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater=menuInflater
        menuInflater.inflate(R.menu.options_menu,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        if(item.itemId==R.id.addpost)
        {
            //upload  activitye gider
            val intent = Intent(applicationContext,UploadActivity::class.java)
            startActivity(intent)

        }
        else if (item.itemId==  R.id.logout)
        {
            logoutfun()
        }
        else if (item.itemId==  R.id.profile)
        {
            val intent = Intent(applicationContext,ProfileActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_other_answer)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        postId =  intent.getStringExtra("postId")

        getDataFromFireStore()



        //recyclerview ayarları
        var layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(DividerItemDecoration(this,1))
        //  carRecycler.addItemDecoration(DividerItemDecoration(this,1))
        adapter = FeedRecyclerAdapter(userEmailFromFB, userQuestionFromFB , userImageFromFB,this,postIdFromFB)
        recyclerView.adapter = adapter

    }

    fun logoutfun(){
        auth.signOut()
        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
        // finish()
        //finishAffinity()
    }

    fun getDataFromFireStore() {
        // db.collection("Users").document(userId).collection("Posts").add(postMap)
        userId = auth.uid.toString()
        println(userId)


        db.collection("Posts").document(postId)
            .addSnapshotListener() { snapshot, exception -> //tüm sorular için
                //   db.collection("Users").document(userId).collection("Posts").addSnapshotListener { snapshot, exception -> //kendi soruları için
                if (exception != null) {
                    Toast.makeText(
                        applicationContext,
                        exception.localizedMessage.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    userId = snapshot?.get("postUserId") as String







                    db.collection("Users").document(userId).collection("userAnswers")
                        .orderBy("date", Query.Direction.DESCENDING)
                        .addSnapshotListener { snapshot, exception -> //kendi soruları için
                            if (exception != null) {
                                Toast.makeText(
                                    applicationContext,
                                    exception.localizedMessage.toString(),
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                if (snapshot != null && !snapshot.isEmpty) {
                                    //yenileme öncesi temizleme yapıytoruz her akışa geldiğinde
                                    userEmailFromFB.clear()
                                    userQuestionFromFB.clear()
                                    userImageFromFB.clear()
                                    postIdFromFB.clear()
                                    val documents = snapshot.documents
                                    for (document in documents) {
                                        val question = document.get("Questions") as String
                                        val timestamp =
                                            document.get("date") as com.google.firebase.Timestamp
                                        val date = timestamp.toDate()
                                        val postId = document.id as String
                                        val userEmail = document.get("userEmail") as String
                                        val downloadUrl = document.get("downloadUrl") as String

                                        /*println(userEmail)
                                println(question)
                                println(date)
                                println(downloadUrl)*/
                                        userEmailFromFB.add(userEmail)
                                        userQuestionFromFB.add(question)
                                        userImageFromFB.add(downloadUrl)
                                        postIdFromFB.add(postId)
                                        adapter!!.notifyDataSetChanged()
                                    }
                                }
                            }
                        }


                }


                // db.collection("Posts").addSnapshotListener { snapshot, exception -> //tüm sorular için

            }
    }
    /*   fun addAnswer(view : View)
       {

           val intent = Intent(applicationContext,AnswersActivity::class.java)
           //intent.putExtra("postId",tttest)
           startActivity(intent)


       }*/
    /*fun test(view : View)
    {

        println(recyclerEmailText.text)
    }*/

    override fun onItemClick(position: Int,uidImage : String,Email : String, Question :String, Image:String) {
        //    Toast.makeText(this, questions.toString(), Toast.LENGTH_SHORT).show()

        val intent = Intent(applicationContext,postClicked::class.java)
        intent.putExtra("postId",uidImage)
        intent.putExtra("Email",Email)
        intent.putExtra("Question",Question)
        intent.putExtra("Image",Image)
        startActivity(intent)

        /*  val intent = Intent(this, CarDetailsActivity::class.java)
          intent.putExtra("CARNAME", item.name)
          intent.putExtra("CARDESC", item.description)
          intent.putExtra("CARLOGO", item.logo.toString())
          startActivity(intent)*/

    }

}
