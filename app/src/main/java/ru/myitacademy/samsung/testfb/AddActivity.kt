package ru.myitacademy.samsung.testfb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
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
             var key = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        nameET = findViewById(R.id.name)
        telnoET = findViewById(R.id.telno)
    }

    fun add(view: View){
        user = Firebase.auth.currentUser!!
        db = Firebase.database.getReference(user.uid)
      val nameF = nameET.text.toString()
      val telnoF = telnoET.text.toString()
      val friend = Friend(key++,nameF,telnoF)
      db.push().setValue(friend)
      finish()
    }
}

class Friend{
    var id = 0
    var name = ""
    var telno = ""
    constructor(id: Int, name: String, telno: String) {
        this.id = id
        this.name = name
        this.telno = telno
    }

}