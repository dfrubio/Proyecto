package com.ucatolica.toffeecompaeroemocional


import java.util.*
//clase para guardar los promedios de las variables que se asosian a als preguntas y se calcula de a cuerdo a las respuestas del usuario
data class Promedio(var variable: String?="", var promedio: Float?= 0f, var fecha: java.util.Date = Calendar.getInstance().time,
                    var correo:String="", var psicologo:String="")
