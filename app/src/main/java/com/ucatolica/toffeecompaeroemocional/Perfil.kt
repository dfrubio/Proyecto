package com.ucatolica.toffeecompaeroemocional

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.ucatolica.toffeecompaeroemocional.oneDayApplication.Companion.prefs

class Perfil : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    var bttnHora: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        val textNombre: TextView = findViewById(R.id.textNombre)
        val textCorreoUsuario: TextView = findViewById(R.id.textCorreo)
        val textNombrePsicologo : TextView = findViewById(R.id.textPsicologo)
        val correoPsicologo: TextView = findViewById(R.id.textPsicologoAsociado)
        val btnLogOut: Button = findViewById(R.id.btnLogOut)
        val cardPerfil: CardView = findViewById(R.id.cardLogin)
        val cardVariables: CardView = findViewById(R.id.cardVariables)


        val indiceDepresion: TextView = findViewById(R.id.tvIndiceDepresion)
        val indiceAnsiedad: TextView = findViewById(R.id.tvIndiceAnsiedad)

        indiceDepresion.text = "Tu índice de depresión está en: ${prefs.getIndiceDepresion()}"
        indiceAnsiedad.text = "Tu índice de ansiedad está en: ${prefs.getIndiceAnsiedad()}"
        textNombrePsicologo.text = prefs.getNombreSpicologo()
        correoPsicologo.text = prefs.getCorreoSpicologo()

        val btnSeleccionarPsicologo: Button = findViewById(R.id.btnSeleccionarPsicologo)
        btnSeleccionarPsicologo.setOnClickListener{
            startActivity(Intent(this, Seleccionar_psicologo::class.java))
            finish()
        }

       val currentUser = FirebaseAuth.getInstance().currentUser

        // bloque de codigo que corresponde al setting de las horas de las notificaciones
       /* bttnHora = findViewById(R.id.button)
        bttnHora?.setOnClickListener {
            val hora = TimePicker{hora, minuto -> mostrarResultado(hora, minuto)}
            hora.show(supportFragmentManager, "timePicker")
        }*/

        db.collection("infobasica")
            .whereEqualTo("correoPrincipal", currentUser?.email)
            .get().addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d("Valores", "${document.data}")
                    textNombre.text = "${document.getString("nombre")} ${document.getString("apellido1")}"
                    textCorreoUsuario.text = document.getString("correoPrincipal")
                }
            }.addOnFailureListener { exception ->
                Log.w("Valores", "Error getting documents: ", exception)
            }

        //Menu Footer
        val bttnNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigationHome)
        bttnNavigationView.selectedItemId = R.id.perfil_menu
        bttnNavigationView.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.tienda_menu -> {
                    startActivity(Intent(this, Home::class.java))
                    true
                }
                R.id.preguntas_menu -> {
                    startActivity(Intent(this, Preguntas_principal::class.java))
                    true
                }
                //Se retira el start activity de esta actividad para evitar reactivarla
                R.id.diario_menu -> {
                    startActivity(Intent(this, Diario::class.java))
                    true
                }
                else -> false
            }
        }

        //Botón para desloguearse (cerrar sesión)
        btnLogOut.setOnClickListener {
            Firebase.auth.signOut()
            //se elimina al psicologo de los datos de la app
            prefs.saveCorreoPsicologo("Selecciona un Psicólogo")
            prefs.saveNombrePsicologo("Selecciona un Psicólogo")
            prefs.saveIndiceDepresion(0f)
            prefs.saveIndiceAnsiedad(0f)
            Toast.makeText(this, "Selecciona un psicólogo al ingresar de nuevo", Toast.LENGTH_SHORT).show()
            //
            finish()
            startActivity(Intent(this,MainActivity::class.java))
        }

        //Botón perfil
        val btnPerfil: Button = findViewById(R.id.buttonPerfil)
        btnPerfil.setOnClickListener {
            cardPerfil.isVisible = true
            cardVariables.isVisible = false
        }
        //Botón variables
        val btnVariables: Button = findViewById(R.id.buttonVariables)
        btnVariables.setOnClickListener {
            cardVariables.isVisible = true
            cardPerfil.isVisible = false
        }
    }

    fun mostrarResultado(hora:Int, minuto:Int){
        bttnHora?.text = modificarHora(hora, minuto)
    }

    fun modificarHora(hora:Int, minuto:Int):String {

        val timeSet: String
        var hora12 = hora
        if (hora > 12) {
            hora12 -= 12
            timeSet = "PM"
        } else if (hora == 0) {
            hora12 += 12
            timeSet = "AM"
        } else if (hora == 12) timeSet = "PM" else timeSet = "AM"

        val minutes: String = if (minuto < 10) "0$minuto" else minuto.toString()

        return "$hora12:$minutes $timeSet"
    }

}


