package ru.myitacademy.samsung.testfb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class GoActivity : AppCompatActivity() {
         var u_id = "non"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_go)
        if(intent.getStringExtra("keyUser")!=null)
            u_id = intent.getStringExtra("keyUser")!!
findViewById<TextView>(R.id.textView2).text = "Добро пожаловать, $u_id!"
        val user = Firebase.auth.currentUser
        val db = Firebase.database.getReference(user!!.uid)

        val lw = findViewById<ListView>(R.id.friends)
        val frList = ArrayList<Friend>()
        val frAdapter = ArrayAdapter<Friend>(this,android.R.layout.simple_list_item_1,frList)
        lw.adapter = frAdapter
        val vel = object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val x = snapshot.children.iterator()
                if(frList.size>0)frList.clear()
               do{
                   x.next().getValue(Friend::class.java)?.let { frList.add(it) }
               }while (x.hasNext())
               frAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) { }

        }
        }



    fun addFriend(view: View) {
        var intent = Intent(this@GoActivity,AddActivity::class.java)
        startActivity(intent)
    }

    fun exit(view: View){
        val mAuth = Firebase.auth
        mAuth.signOut()
        finish()
    }

}