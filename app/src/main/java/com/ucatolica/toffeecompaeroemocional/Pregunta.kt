package com.ucatolica.toffeecompaeroemocional
import com.google.firebase.firestore.DocumentReference

data class Pregunta (var categoria: String?= "", var idPregunta: String?= "", var periodicidad: String?= "",
                     var pregunta: String?= "", var variable1: String?= "", var variable2:String?= "", var variable3: String?= "")