package com.ozan.repoapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ozan.repoapplication.R

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var buttonLogin: Button
    private lateinit var buttonVisitor: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonVisitor = findViewById(R.id.button_visitor)
        buttonLogin = findViewById(R.id.button_anonymous)

        auth = Firebase.auth

        auth.createUserWithEmailAndPassword("oorfa@hotmail.com", "123456789")
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                } else {

                }

                // ...
            }


        buttonLogin.setOnClickListener {

            auth.signInWithEmailAndPassword("oorfa@hotmail.com", "123456789")
                .addOnCanceledListener(this) {
                    updateFun(null)
                }
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        updateFun(auth.currentUser)

                    } else {


                    }

                    // ...
                }


        }

        buttonVisitor.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }


    }

    private fun updateFun(user: FirebaseUser?) {

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        //updateFun(currentUser)


    }
}