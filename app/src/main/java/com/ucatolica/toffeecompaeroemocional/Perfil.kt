package com.ucatolica.toffeecompaeroemocional

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class Perfil : AppCompatActivity() {
    //instanciar el objeto de Home
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        val textNombre: TextView = findViewById(R.id.textNombreUsario)
        val textCorreoUsuario: TextView = findViewById(R.id.textCorreodeUsuario)
        val textNombrePsicologo : TextView = findViewById(R.id.textPsicologoAsociado)

        textNombre.text = consultarDatos()
    }


    fun consultarDatos(): CharSequence{

        var prestates:String = ""

        db.collection("infobasica").
        whereEqualTo("correoPrincipal","dfrubio62@ucatolica.edu.co")
            .get().addOnSuccessListener {
                prestates = it.documents.toString()
            }
        Toast.makeText(this, prestates, Toast.LENGTH_SHORT).show()
        return prestates
    }

}