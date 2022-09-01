package com.example.secondapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class login : AppCompatActivity() {

    private lateinit var edt_email:EditText
    private lateinit var edt_pwd:EditText
    private lateinit var loginbtn:Button
    private lateinit var signupbtn:Button

    private lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_SecondApp)
        setContentView(R.layout.activity_login)

        edt_email=findViewById<EditText>(R.id.Email)
        edt_pwd=findViewById<EditText>(R.id.Password)
        loginbtn=findViewById<Button>(R.id.loginbtn)
        signupbtn=findViewById<Button>(R.id.signupbtn)

        mAuth= FirebaseAuth.getInstance()

        signupbtn.setOnClickListener {
            val intent=Intent(this,signup::class.java)
            startActivity(intent)
        }
        loginbtn.setOnClickListener{
            val email=edt_email.text.toString()
            val password=edt_pwd.text.toString()

            log_in(email,password)
        }
    }

    private fun log_in(email:String,password:String){
        //logic for signing in user
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent=Intent(this@login,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this@login,"User does not exist.",Toast.LENGTH_SHORT).show()
                }
            }

    }
}