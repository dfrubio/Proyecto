package com.ucatolica.toffeecompaeroemocional

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.ucatolica.toffeecompaeroemocional.oneDayApplication.Companion.prefs

// https://www.youtube.com/watch?v=k3zoVAMuW5w Turtorial recycler views
class seleccionar_psicologo_adapter (var listaPsicologos: MutableList<psicologo>) : RecyclerView.Adapter<seleccionar_psicologo_adapter.spciologoViewholder>() {

    inner class spciologoViewholder (itemView: View) : RecyclerView.ViewHolder(itemView){
        private val nombre_psicologo: TextView = itemView.findViewById(R.id.nombre_psicologo_rv)
        private val correo_psicologo: TextView = itemView.findViewById(R.id.correo_psicologo_rv)
        private val arrow_seleccion: ImageView = itemView.findViewById(R.id.arrow_psicologo_rv)
        fun render(psicologo: psicologo){
            nombre_psicologo.text = psicologo.nombre
            correo_psicologo.text = psicologo.correoPrincipal

            arrow_seleccion.setOnClickListener{
                prefs.saveCorreoPsicologo(psicologo.correoPrincipal.toString())
                prefs.saveNombrePsicologo(psicologo.nombre.toString())
                Toast.makeText(itemView.context, "Psicólogo seleccionado \uD83E\uDD20", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): spciologoViewholder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return spciologoViewholder(layoutInflater.inflate(R.layout.psicologo_rv, parent, false))
    }


    override fun onBindViewHolder(holder: spciologoViewholder, position: Int) {
        val item = listaPsicologos[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = listaPsicologos.size

    fun filtrar(listaFiltrada: MutableList<psicologo>){
        this.listaPsicologos = listaFiltrada
        notifyDataSetChanged()
    }

}