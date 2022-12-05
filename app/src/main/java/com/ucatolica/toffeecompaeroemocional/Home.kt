package com.ucatolica.toffeecompaeroemocional

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.github.mikephil.charting.charts.Chart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.toObject
import java.util.Calendar

class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //Esta va a ser temporalmente la pantalla de tienda, mientras agregamos el botón home al footer

        val bttnNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigationHome)
        bttnNavigationView.selectedItemId = R.id.tienda_menu
        //footer
        bttnNavigationView.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.perfil_menu -> {
                    startActivity(Intent(this, Perfil::class.java))
                    true
                }
                R.id.preguntas_menu -> {
                    startActivity(Intent(this, Preguntas_principal::class.java))
                    true
                }
                //Se retira el start activity de esta actividad para evitar reactivarla
                R.id.diario_menu -> {
                    startActivity(Intent(this, Diario::class.java))
                    true
                }
                else -> false
            }
        }

        //test graficas
        /*val dayChart = mutableListOf<Entry>()
        val dayChart2 = mutableListOf<Entry>()
        val chart: LineChart = findViewById(R.id.lineChart)

        val xAxis: XAxis = chart.xAxis

        xAxis.gridColor = R.color.purple_200

        dayChart.add(Entry(6f,5f))
        dayChart.add(Entry(7f,7f))
        dayChart.add(Entry(8f,14f))
        dayChart.add(Entry(9f,3f))
        dayChart.add(Entry(10f,2f))
        dayChart.add(Entry(11f,8f))
        dayChart.add(Entry(12f,1f))

        dayChart2.add(Entry(6f,1f))
        dayChart2.add(Entry(7f,4f))
        dayChart2.add(Entry(8f,7f))
        dayChart2.add(Entry(9f,3f))
        dayChart2.add(Entry(10f,10f))
        dayChart2.add(Entry(11f,8f))

        val chartDataset = LineDataSet(dayChart,"Nombre del gráfico")
        val chartDataset2 = LineDataSet(dayChart2,"Nombre del gráfico")
        val lineData = LineData(chartDataset,chartDataset2)
        chart.data = lineData*/

    }
}

