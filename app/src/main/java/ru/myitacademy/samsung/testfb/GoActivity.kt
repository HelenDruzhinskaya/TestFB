package ru.myitacademy.samsung.testfb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
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
        }



    fun addFriend(view: View) {
        var intent = Intent(this@GoActivity,AddActivity::class.java)
        intent.putExtra("keyUser",u_id)
        startActivity(intent)
    }

    fun exit(view: View){
        val mAuth = Firebase.auth
        mAuth.signOut()
        finish()
    }

}