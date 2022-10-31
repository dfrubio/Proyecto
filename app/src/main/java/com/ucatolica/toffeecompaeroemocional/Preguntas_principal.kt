package com.ucatolica.toffeecompaeroemocional

import android.graphics.ColorFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ServerTimestamp
import com.google.firebase.firestore.ktx.toObject
import com.google.protobuf.Empty
import java.util.Calendar

import java.util.Date
import kotlin.system.measureTimeMillis


class Preguntas_principal : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    val listaRespuestas = mutableListOf<Float>()
    var listaPreguntas = mutableListOf<Pregunta>()
    val listaPregDiaria = mutableListOf<Pregunta>()
    val listaPregAleatoria = mutableListOf<Pregunta>()
    val listaPregSuicida = mutableListOf<Pregunta>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preguntas_principal)

        val container = findViewById<ShimmerFrameLayout>(R.id.shimmer_view_container)
        val btnSubirRespuestas: Button = findViewById(R.id.btnSubirRespuestas)
        val currentUser = FirebaseAuth.getInstance().currentUser

        container.startShimmer()
        cargarPreguntas()
        btnSubirRespuestas.setOnClickListener {
            val ingertoPrueba = Respuesta()

            for (n: Int in 0..listaPreguntas.size-1){
                ingertoPrueba.pregunta = listaPreguntas[n].pregunta
                ingertoPrueba.idPregunta = listaPreguntas[n].idPregunta
                ingertoPrueba.respuesta = listaRespuestas[n]
                ingertoPrueba.fecha = Calendar.getInstance().time
                ingertoPrueba.correo = currentUser?.email.toString()

                db.collection("respuestasAndroid").add(ingertoPrueba).addOnSuccessListener {
                    Log.d("Subida", "Respuestas subidas con éxito $ingertoPrueba")
                }
            }
        }
    }

    fun cargarPreguntas(){
        val rvPreguntas : RecyclerView = findViewById(R.id.recyclerPregunta)
        val container = findViewById<ShimmerFrameLayout>(R.id.shimmer_view_container)

        db.collection("preguntas")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val preg = document.toObject<Pregunta>()
                    if (preg.periodicidad.toString() == "Diaria"){
                        listaPregDiaria.add(preg)
                    }else if(preg.periodicidad.toString() == "Aleatoria"){
                        listaPregAleatoria.add(preg)
                    }else if (preg.periodicidad.toString() == "Condicional"){
                        listaPregSuicida.add(preg)
                    }
                    //listaPreguntas.add(preg)
                    //Log.d("Preguntas Lista", "listaPreguntas: $listaPreguntas")
                }
                Log.d("Preguntas Lista", "Lista Diaria: $listaPregDiaria")
                Log.d("Preguntas Lista", "Lista Aleatoria: $listaPregAleatoria")
                Log.d("Preguntas Lista", "Lista Suicida: $listaPregSuicida")
                listaPreguntas = listaPregDiaria
                listaPregAleatoria.shuffle()
                listaPreguntas.add(listaPregAleatoria[0])
                listaPreguntas.add(listaPregAleatoria[1])
                for (i:Int in 0 .. listaPreguntas.size-1){
                    listaRespuestas.add(0f)
                }
                listaPreguntas.shuffle()
                container.stopShimmer()
                container.hideShimmer()
                container.visibility = View.GONE
                rvPreguntas.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                rvPreguntas.adapter = PreguntaAdapter(listaPreguntas, {respuestasPreguntas(it)})
            }
            .addOnFailureListener { exception ->
                Log.d("Preguntas", "Error getting documents: $exception", exception)
            }
    }

    fun respuestasPreguntas( respuesta: List<Float>) {
        listaRespuestas[respuesta[0].toInt()] = respuesta[1]
        if (listaRespuestas.contains(0f)){
            inhabilitarBoton()
        }else{
            habilitarBoton()
        }
        Log.d("Respuestas", "Lista test $listaRespuestas")
    }

    private fun habilitarBoton() {
        val btnSubirRespuestas: Button = findViewById(R.id.btnSubirRespuestas)
        btnSubirRespuestas.isClickable = true
        btnSubirRespuestas.isVisible = true
    }

    private fun inhabilitarBoton() {
        val btnSubirRespuestas: Button = findViewById(R.id.btnSubirRespuestas)
        btnSubirRespuestas.isClickable = false
    }
}