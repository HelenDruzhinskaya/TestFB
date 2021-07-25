package ru.myitacademy.samsung.testfb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AddActivity : AppCompatActivity() {
    lateinit var nameET: EditText
    lateinit var telnoET: EditText
    lateinit var db: DatabaseReference
    lateinit var user: FirebaseUser
    var k=0
    var u_id = "non"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        nameET = findViewById(R.id.name)
        telnoET = findViewById(R.id.telno)
        if(intent.getStringExtra("keyUser")!=null)
            u_id = intent.getStringExtra("keyUser")!!
    }

    fun add(view: View){
        user = Firebase.auth.currentUser!!
        db = Firebase.database.getReference(user.uid)
      val nameF = nameET.text.toString()
      val telnoF = telnoET.text.toString()
      val friend = Friend(nameF,telnoF)
    db.child((findViewById<EditText>(R.id.nick)).text.toString()).setValue(friend)
        Toast.makeText(this,"Добавлено",Toast.LENGTH_SHORT).show()
      finish()
    }
}

class Friend{
    var name = ""
    var telno = ""
    constructor(name: String, telno: String) {
        this.name = name
        this.telno = telno
    }

    override fun toString(): String {
        return "name='$name', telno='$telno'"
    }


}