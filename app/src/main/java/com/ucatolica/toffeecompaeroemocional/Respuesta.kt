package com.ucatolica.toffeecompaeroemocional


import java.util.*


data class Respuesta(var pregunta: String?="", var idPregunta: String?="", var respuesta: Float?= 0f, var fecha: java.util.Date = Calendar.getInstance().time,
                     var correo:String="" )