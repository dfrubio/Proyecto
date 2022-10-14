package com.ucatolica.toffeecompaeroemocional

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore

class Home : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val textPregunta : TextView = findViewById(R.id.textViewPregunta)
        val btnPregunta : Button = findViewById(R.id.buttonPregunta)
        btnPregunta.setOnClickListener {
            db.collection("preguntas").document("Ko6AeR2WNP9e453qHWSr").get().addOnSuccessListener {
                textPregunta.text = it.getString("pregunta")
            }
        }
    }
}