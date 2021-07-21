package ru.myitacademy.samsung.testfb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth //база пользователей
    lateinit var login: EditText    //поле ввода почты
    lateinit var passw: EditText    //поле ввода пароля
    var try_count = 0               //количество попыток авторизации
    val try_limit = 3               //разрешенное количество ошибок авторизации
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        login = findViewById(R.id.editTextTextEmailAddress)
        passw = findViewById(R.id.editTextTextPassword)
        auth = Firebase.auth
    }

    fun signIn(view: View){
        //читаем введённые параметры авторизации
       val log = login.text.toString()  //считали почту
       val psw = passw.text.toString()   //считали пароль
       if(log!=""&&psw!="") {
           auth.signInWithEmailAndPassword(log,psw).addOnCompleteListener(this) { task->
               if(!task.isSuccessful) {
                   var txt = ""       //текст для вставки в тост
                   if (++try_count < try_limit)
                       txt = "Неудачная попытка $try_count из $try_limit\n Попробуйте снова"
                   else {txt = "Вам отказано в использовании приложения"; finish()}
                   var toast = Toast.makeText(this,txt,Toast.LENGTH_SHORT)
                   toast.setGravity(Gravity.CENTER,0,0)
                   toast.show()
                   login.setText("")
                   passw.setText("")
               }
       else {
           val user = auth.currentUser
           updateUI(user)
               }
           }
       }
      else Toast.makeText(this,"не заполнены поля!", Toast.LENGTH_SHORT).show()
        }

    private fun updateUI(user: FirebaseUser?) {
        login.setText("")
        passw.setText("")
        if(user!=null){
         var intent = Intent(this@MainActivity,GoActivity::class.java)
         intent.putExtra("keyUser", user.email)
        startActivity(intent)
       // finish()
        }
    }

    fun addUser(view: View) {
        val log = login.text.toString()  //считали почту
        val psw = passw.text.toString()   //считали пароль
        var txt = ""
        if(log!=""&&psw!="")
        auth.createUserWithEmailAndPassword(log, psw).addOnCompleteListener(this) { task ->
            if (task.isSuccessful)   {
                val user = auth.currentUser
                txt = "удачно, заходите в систему " + user?.email
            //    updateUI(user)
            }
            else {
            txt = "отказано в регистрации:\nадрес почты в формате name@host.domain\n пароль не менее 6 знаков"
                login.setText("")
                passw.setText("")
            }
            var toast = Toast.makeText(this, txt, Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
        else Toast.makeText(this,"не заполнены поля!", Toast.LENGTH_SHORT).show()
    }

    fun delUser(view: View){
        val user = Firebase.auth.currentUser
        Toast.makeText(this,"удаляем: " +user?.email,Toast.LENGTH_SHORT).show()
        if (user != null) {
            user.delete()
        }
    }
}