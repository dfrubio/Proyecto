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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //Esta va a ser temporalmente la pantalla de tienda, mientras agregamos el botón home al footer

        val bttnNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigationHome)
        bttnNavigationView.selectedItemId = R.id.tienda_menu
        bttnNavigationView.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.perfil_menu -> {
                    startActivity(Intent(this, Perfil::class.java))
                    true
                }
                R.id.preguntas_menu -> {
                    startActivity(Intent(this, Preguntas_principal::class.java))
                    true
                }
                else -> false
            }
        }
    }
}

