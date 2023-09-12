package com.ucatolica.toffeecompaeroemocional

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class Seleccionar_psicologo : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    var listaPsicologos = mutableListOf<psicologo>()
    private lateinit var adaptador: seleccionar_psicologo_adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccionar_psicologo)

        val buscador :EditText = findViewById(R.id.editText_BuscarPsicologo)
        val btnRegresar: ImageButton = findViewById(R.id.btnRegresar_SelecionarPsicologo)

        llenarListaPsicologos()
        buscador.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                filtrar(s.toString())
            }

        })

        btnRegresar.setOnClickListener{
            startActivity(Intent(this, Perfil::class.java))
            finish()
        }

    }

    private fun llenarListaPsicologos() {
        val rvPsicologo: RecyclerView = findViewById(R.id.recyclerView_psicologo)
        db.collection("profesional")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val psico = document.toObject<psicologo>()
                    //agregar aquí un condicional que filtre si el psicologo es de la entidad de salud del paciente
                    listaPsicologos.add(psico)
                }
                Log.d("Lista Psicologo", "Lista Psicologo: $listaPsicologos")
            }
        rvPsicologo.layoutManager = LinearLayoutManager(this)
        adaptador = seleccionar_psicologo_adapter(listaPsicologos)
        rvPsicologo.adapter = adaptador
    }

    fun filtrar(texto: String){
        var listafiltrada = mutableListOf<psicologo>()
        listaPsicologos.forEach{
            if((it.nombre?.toLowerCase()?.contains(texto.toLowerCase())!!)){
                listafiltrada.add(it)
            }else if((it.correoPrincipal?.toLowerCase()?.contains(texto.toLowerCase())!!)){
                listafiltrada.add(it)
            }
        }
        adaptador.filtrar(listafiltrada)
    }
}


