package com.ucatolica.toffeecompaeroemocional

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val btnPreguntas: Button = findViewById(R.id.buttonPreguntas)

        btnPreguntas.setOnClickListener{startActivity(Intent(this,Preguntas_principal::class.java))}
    }
}

