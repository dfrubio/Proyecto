package com.ucatolica.toffeecompaeroemocional

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler

class PreguntaAdapter(var listaPreguntas: MutableList<Pregunta>): RecyclerView.Adapter<PreguntaAdapter.PreguntaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreguntaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.preguntas_to_recycler, parent, false)
        return PreguntaViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: PreguntaViewHolder, position: Int) {
        val item = listaPreguntas[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = listaPreguntas.size

    inner class PreguntaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textPreg: TextView = itemView.findViewById(R.id.tvPreguntaRecycler)
        fun render(preg: Pregunta){
            textPreg.text = preg.pregunta
        }
    }
}