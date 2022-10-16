package com.ucatolica.toffeecompaeroemocional

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.toObject

class Home : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val textPregunta : TextView = findViewById(R.id.textViewPregunta)
        val btnPregunta : Button = findViewById(R.id.buttonPregunta)
        val listaPreguntas = mutableListOf<Pregunta>()
        val rvPreguntas : RecyclerView = findViewById(R.id.recyclerPregunta)
        val btnPerfil : Button = findViewById(R.id.bttnPerfil)

        btnPregunta.setOnClickListener {
            db.collection("preguntas")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val preg = document.toObject<Pregunta>()
                        listaPreguntas.add(preg)
                        Log.d("Preguntas Lista", "Dentro del botón: $listaPreguntas")
                    }
                    rvPreguntas.layoutManager = LinearLayoutManager(this)
                    rvPreguntas.adapter = PreguntaAdapter(listaPreguntas)
                }
                .addOnFailureListener { exception ->
                    Log.d("Preguntas", "Error getting documents: $exception", exception)
                }
            }
        Log.d("Preguntas Lista ", "Fuera del botón : $listaPreguntas")



        btnPerfil.setOnClickListener {
            startActivity(Intent(this, Perfil::class.java))
        }
    }

}

