package com.ucatolica.toffeecompaeroemocional

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore


class Perfil : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        val textNombre: TextView = findViewById(R.id.textNombreUsuario)
        val textCorreoUsuario: TextView = findViewById(R.id.textCorreodeUsuario)
        val textNombrePsicologo : TextView = findViewById(R.id.textPsicologoAsociado)


        db.collection("infobasica")
            .whereEqualTo("correoPrincipal", "dfrubio62@ucatolica.edu.co")
            .get().addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d("Valores", "${document.data}")
                    textNombre.text = document.getString("nombre")+ " " + document.getString("apellido1")
                    textCorreoUsuario.text = document.getString("correoPrincipal")
                }
            }.addOnFailureListener { exception ->
                Log.w("Valores", "Error getting documents: ", exception)
            }
    }
}