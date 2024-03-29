package com.ucatolica.toffeecompaeroemocional

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.slider.Slider

class PreguntaAdapter(var listaPreguntas: MutableList<Pregunta>, private val respuestasPreguntas: (List<Float>)->Unit ): RecyclerView.Adapter<PreguntaAdapter.PreguntaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreguntaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.preguntas_to_recycler, parent, false)
        return PreguntaViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: PreguntaViewHolder, position: Int) {
        val item = listaPreguntas[position]
        holder.render(item, respuestasPreguntas, position)
    }

    override fun getItemCount(): Int = listaPreguntas.size

     inner class PreguntaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textPreg: TextView = itemView.findViewById(R.id.tvPreguntaRecycler)
        private val slider: Slider = itemView.findViewById(R.id.slider)
        private val nada: ImageView = itemView.findViewById(R.id.imageViewNada)
        private val leve: ImageView = itemView.findViewById(R.id.imageViewLeve)
        private val moderado: ImageView = itemView.findViewById(R.id.imageViewModerado)
        private val severo: ImageView = itemView.findViewById(R.id.imageViewSevero)
        private val extremo: ImageView = itemView.findViewById(R.id.imageViewExtremo)

        fun render(preg: Pregunta,  respuestasPreguntas: (List<Float>) -> Unit, position: Int){

            textPreg.text = preg.pregunta
            val primeraRespuesta = listOf<Float>(adapterPosition.toFloat(),slider.value)
            respuestasPreguntas(primeraRespuesta)
            setBackground(nada, leve, moderado, severo, extremo)
            itemView.setOnClickListener {
                Toast.makeText(itemView.context, "Hola estoy en $position", Toast.LENGTH_SHORT).show()
                posicionSlider = position
            }

            itemView.setOnClickListener {
                posicionSlider = position
            }



            slider.addOnChangeListener { slider, value, fromUser ->
                when(value){
                1f -> setBackground(nada, leve, moderado, severo, extremo)
                2f -> setBackground(leve, nada, moderado, severo, extremo)
                3f -> setBackground(moderado, nada, leve, severo, extremo)
                4f -> setBackground(severo, nada, leve, moderado, extremo)
                5f -> setBackground(extremo, nada, leve, moderado, severo)
                }

                val variableTest = listOf<Float>(adapterPosition.toFloat(),value)
                respuestasPreguntas(variableTest)
                posicionSlider = position
               //val instacia = Preguntas_principal()


            } //Funciona el listener del slider

            nada.setOnClickListener { setBackground(nada, leve, moderado, severo, extremo)
                slider.value = 1F
                posicionSlider = position} //puedo setear el valor del slider
            leve.setOnClickListener { setBackground(leve, nada, moderado, severo, extremo)
                slider.value = 2F
                posicionSlider = position}
            moderado.setOnClickListener { setBackground(moderado, nada, leve, severo, extremo)
                slider.value = 3F
                posicionSlider = position}
            severo.setOnClickListener { setBackground(severo, nada, leve, moderado, extremo)
                slider.value = 4F
                posicionSlider = position}
            extremo.setOnClickListener { setBackground(extremo, nada, leve, moderado, severo)
                slider.value = 5F
                posicionSlider = position}

        }
        fun setBackground(emocion: ImageView, cambio1:ImageView, cambio2:ImageView, cambio3:ImageView, cambio4:ImageView){
            emocion.setBackgroundResource(R.drawable.fondo_seleccionado)
            cambio1.setBackgroundResource(R.color.white)
            cambio2.setBackgroundResource(R.color.white)
            cambio3.setBackgroundResource(R.color.white)
            cambio4.setBackgroundResource(R.color.white)
        }

    }
}


