package com.ucatolica.toffeecompaeroemocional

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class Preguntas_principal : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preguntas_principal)

        val container = findViewById<ShimmerFrameLayout>(R.id.shimmer_view_container)
        container.startShimmer()
        cargarPreguntas()
    }

    fun cargarPreguntas(){
        val listaPreguntas = mutableListOf<Pregunta>()
        val rvPreguntas : RecyclerView = findViewById(R.id.recyclerPregunta)
        val container = findViewById<ShimmerFrameLayout>(R.id.shimmer_view_container)

        db.collection("preguntas")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val preg = document.toObject<Pregunta>()
                    listaPreguntas.add(preg)
                    Log.d("Preguntas Lista", "Dentro del botón: $listaPreguntas")
                }
                container.stopShimmer()
                container.hideShimmer()
                container.visibility = View.GONE
                rvPreguntas.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                rvPreguntas.adapter = PreguntaAdapter(listaPreguntas)
            }
            .addOnFailureListener { exception ->
                Log.d("Preguntas", "Error getting documents: $exception", exception)
            }
    }
}