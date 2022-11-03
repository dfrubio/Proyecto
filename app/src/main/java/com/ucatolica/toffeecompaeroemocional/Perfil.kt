package com.ucatolica.toffeecompaeroemocional

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class Perfil : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    var bttnHora: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        val textNombre: TextView = findViewById(R.id.textNombre)
        val textCorreoUsuario: TextView = findViewById(R.id.textCorreo)
        val textNombrePsicologo : TextView = findViewById(R.id.textPsicologo)
        val btnLogOut: Button = findViewById(R.id.btnLogOut)

        val currentUser = FirebaseAuth.getInstance().currentUser
        bttnHora = findViewById(R.id.button)
        bttnHora?.setOnClickListener {
            val hora = TimePicker{hora, minuto -> mostrarResultado(hora, minuto)}
            hora.show(supportFragmentManager, "timePicker")
        }

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
                else -> false
            }
        }

        //Botón para desloguearse
        btnLogOut.setOnClickListener {
            Firebase.auth.signOut()
            finish()
            startActivity(Intent(this,MainActivity::class.java))
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


