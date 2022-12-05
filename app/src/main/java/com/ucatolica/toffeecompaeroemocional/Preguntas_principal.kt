package com.ucatolica.toffeecompaeroemocional

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.ucatolica.toffeecompaeroemocional.oneDayApplication.Companion.prefs
import java.util.Calendar


class Preguntas_principal : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    val listaRespuestas = mutableListOf<Float>()
    var listaPreguntas = mutableListOf<Pregunta>()
    val listaPregDiaria = mutableListOf<Pregunta>()
    val listaPregAleatoria = mutableListOf<Pregunta>()
    val listaPregSuicida = mutableListOf<Pregunta>()
    var listaRespuestasDepresion = mutableListOf<Float>()
    var listaRespuestasAnsiedad = mutableListOf<Float>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preguntas_principal)

        val container = findViewById<ShimmerFrameLayout>(R.id.shimmer_view_container)
        val btnSubirRespuestas: Button = findViewById(R.id.btnSubirRespuestas)
        val currentUser = FirebaseAuth.getInstance().currentUser
        // Menú foooter
        val bttnNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bttnNavigationView.selectedItemId = R.id.preguntas_menu
        bttnNavigationView.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.perfil_menu -> {
                    startActivity(Intent(this, Perfil::class.java))
                    true
                }
                //Se retira el start activity de esta actividad para evitar reactivarla
                R.id.tienda_menu -> {
                    startActivity(Intent(this, Home::class.java))
                    true
                }
                R.id.diario_menu -> {
                    startActivity(Intent(this, Diario::class.java))
                    true
                }
                else -> false
            }
        }

        container.startShimmer()

        // Comprobación de respuestas diariamente
        db.collection("respuestasAndroid").get().addOnSuccessListener { result ->
            var flag:Boolean = false
            for (document in result){
                if (document.get("correo") == currentUser?.email.toString()) {
                    //diferencia calcula el tiempo que ha pasado entre hoy y las respuestas
                    val diferencia = (Calendar.getInstance().time).time - (document.getDate("fecha"))!!.time
                    if (diferencia < 86400000){ //milisegundos en 24 horas
                        Log.d("Usuario", "Pasó MENOS de un día")
                        flag = true
                        break
                    }
                }
            }
            if (flag){
                val cardNoPreguntas: CardView = findViewById(R.id.cardNoHayPreguntas)
                container.stopShimmer()
                container.hideShimmer()
                container.visibility = View.GONE
                cardNoPreguntas.isVisible = true
            }else{
                cargarPreguntas()
            }
        }
        //Depliegue de preguntas

        //boton para subir respuestas
        btnSubirRespuestas.setOnClickListener {
            val ingertoPrueba = Respuesta()

            for (n: Int in 0..listaPreguntas.size-1){
                ingertoPrueba.pregunta = listaPreguntas[n].pregunta
                ingertoPrueba.idPregunta = listaPreguntas[n].idPregunta
                ingertoPrueba.respuesta = listaRespuestas[n]
                ingertoPrueba.fecha = Calendar.getInstance().time
                ingertoPrueba.correo = currentUser?.email.toString()

                //Condicional para llenar la lista depresion
                if((listaPreguntas[n].variable1 == "Depresión")||(listaPreguntas[n].variable2 == "Depresión")||(listaPreguntas[n].variable3 == "Depresión")){
                    listaRespuestasDepresion.add(listaRespuestas[n])
                }
                //Condicional para llenar la lista de ansiedad
                if((listaPreguntas[n].variable1 == "Ansiedad")||(listaPreguntas[n].variable2 == "Ansiedad")||(listaPreguntas[n].variable3 == "Ansiedad")){
                    listaRespuestasAnsiedad.add(listaRespuestas[n])
                }
                //subida de respuestas a Firestore
                db.collection("respuestasAndroid").add(ingertoPrueba).addOnSuccessListener {
                    Log.d("Subida", "Respuestas subidas con éxito $ingertoPrueba")

                }.addOnFailureListener { Toast.makeText(this, "Ha habido un error, comprueba tu conexión", Toast.LENGTH_SHORT).show() }
            }

            prefs.saveIndiceDepresion(listaRespuestasDepresion.average().toFloat())
            prefs.saveIndiceAnsiedad(listaRespuestasAnsiedad.average().toFloat())

            Toast.makeText(this, "Respuestas subidas con éxito \uD83E\uDD20", Toast.LENGTH_SHORT).show()

            startActivity(Intent(this,Preguntas_principal::class.java))
            finish()

            Log.d("Variables", "lista de depresión: $listaRespuestasDepresion, y su promedio es ${listaRespuestasDepresion.average()}")
            Log.d("Variables", "lista de ansiedad: $listaRespuestasAnsiedad, y su promedio es ${listaRespuestasAnsiedad.average()}")
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
                    //Test para ver las variables de las preguntas
                    Log.d("Variables", "las variables de la pregunta $i son |${listaPreguntas[i].variable1}| - |${listaPreguntas[i].variable2}| - |${listaPreguntas[i].variable3}|")
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