package com.ucatolica.toffeecompaeroemocional

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.google.android.material.slider.Slider

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
        private val slider: Slider = itemView.findViewById(R.id.slider)
        private val nada: ImageView = itemView.findViewById(R.id.imageView)
        fun render(preg: Pregunta){
            textPreg.text = preg.pregunta
            slider.addOnChangeListener { slider, value, fromUser -> Toast.makeText(textPreg.context, "soy un slider en $value", Toast.LENGTH_SHORT).show() } //Funciona el listener del slider
            textPreg.setOnClickListener { Toast.makeText(textPreg.context, "soy un texto", Toast.LENGTH_SHORT).show() }
            nada.setOnClickListener { Toast.makeText(textPreg.context, "nada", Toast.LENGTH_SHORT).show()
            slider.value = 2F } //puedo setear el valor del slider
        }
    }
}