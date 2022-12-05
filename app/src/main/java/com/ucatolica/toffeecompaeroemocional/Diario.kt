package com.ucatolica.toffeecompaeroemocional

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class Diario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diario)

        //menu footer
        val bttnNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bttnNavigationView.selectedItemId = R.id.diario_menu
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
    }
}