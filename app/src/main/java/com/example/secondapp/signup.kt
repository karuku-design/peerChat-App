package com.example.secondapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class signup : AppCompatActivity() {

    private lateinit var edt_name: EditText
    private lateinit var edt_email: EditText
    private lateinit var edt_pwd: EditText
    private lateinit var loginbtn: Button
    private lateinit var signupbtn: Button

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_SecondApp)
        setContentView(R.layout.activity_signup)

        edt_name=findViewById<EditText>(R.id.Name)
        edt_email=findViewById<EditText>(R.id.Email)
        edt_pwd=findViewById<EditText>(R.id.Password)
        signupbtn=findViewById<Button>(R.id.btnsignup)

        mAuth= FirebaseAuth.getInstance()

        signupbtn.setOnClickListener{
            val name=edt_name.text.toString()
            val email=edt_email.text.toString()
            val password=edt_pwd.text.toString()

            sign_up(name,email,password)
        }
    }

    private fun sign_up(name:String,email:String,password:String){
        //logic for signing in user
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!)

                    val intent=Intent(this@signup,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this@signup,"Some error occured",Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun addUserToDatabase(name: String,email: String,uid:String){
        mDbRef=FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(user(name, email, uid))
    }

}