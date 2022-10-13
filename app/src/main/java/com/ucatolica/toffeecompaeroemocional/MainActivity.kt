package com.ucatolica.toffeecompaeroemocional

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textEmail:EditText = findViewById(R.id.editTextEmail)
        val textPass:EditText = findViewById(R.id.editTextPassword)

        val logInButton = findViewById<Button>(R.id.logInButton)
        logInButton.setOnClickListener {
            if (textEmail.text.isNotEmpty() && textPass.text.isNotEmpty()){
                logIn(textEmail.text.toString(), textPass.text.toString())
            }else{
                Toast.makeText(this, "Campos vacíos", Toast.LENGTH_LONG).show()
            }

        }

    }

    private fun logIn(email: String, pass: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,pass).addOnCompleteListener {
            if (it.isSuccessful){
                val intent = Intent(this,Home::class.java)
                startActivity(intent)
            }
        }
    }


}