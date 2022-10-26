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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.toObject

class Home : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val textPregunta : TextView = findViewById(R.id.textViewPregunta)
        val btnPregunta : Button = findViewById(R.id.buttonPregunta)
        val listaPreguntas = mutableListOf<Pregunta>()
        val rvPreguntas : RecyclerView = findViewById(R.id.recyclerPregunta)
        val bttnNav : BottomNavigationView = findViewById(R.id.bottom_navigation)

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



        bttnNav.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.page_1 ->{
                    startActivity(Intent(this, Perfil::class.java))
                    return@setOnNavigationItemReselectedListener
                }
                R.id.page_2 ->{

                }
                R.id.page_3->{

                }
            }
        }
    }
}

