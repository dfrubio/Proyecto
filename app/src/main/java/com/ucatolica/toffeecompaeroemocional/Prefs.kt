package com.ucatolica.toffeecompaeroemocional

import android.content.Context

class Prefs(val context: Context) {
    val storage = context.getSharedPreferences("Mydb", 0)

    fun saveIndiceDepresion(depresison:Float){
        storage.edit().putFloat("INDICE_DEPRESION",depresison).apply()
    }

    fun saveIndiceAnsiedad(ansiedad:Float){
        storage.edit().putFloat("INDICE_ANSIEDAD",ansiedad).apply()
    }

    fun getIndiceDepresion():Float{
        return storage.getFloat("INDICE_DEPRESION",0f)
    }

    fun getIndiceAnsiedad():Float{
        return storage.getFloat("INDICE_ANSIEDAD",0f)
    }
}