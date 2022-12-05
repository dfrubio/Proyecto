package com.ucatolica.toffeecompaeroemocional

import android.app.Application



class oneDayApplication: Application() {

    companion object{
        lateinit var prefs: Prefs
    }

    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)
    }
}