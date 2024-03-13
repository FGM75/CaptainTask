package com.example.captaintask

import DatabaseHelper
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Filter : AppCompatActivity() {


    private lateinit var listaCompraAdapter: ListaCompraAdapter
    private lateinit var recyclerView: RecyclerView
    private val listasDeCompra = mutableListOf<ListaCompra>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.filter)


        // Configurar RecyclerView
        recyclerView = findViewById(R.id.tuRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        listaCompraAdapter = ListaCompraAdapter(listasDeCompra)
        recyclerView.adapter = listaCompraAdapter

        // Obtener las listas de compras desde la base de datos
        obtenerListasCompraDesdeDB()
    }

    private fun obtenerListasCompraDesdeDB() {
        // Obtener las ListasCompra desde la base de datos
        val dbHelper = DatabaseHelper(this)
        listasDeCompra.clear() // Limpiar la lista actual antes de agregar las nuevas listas
        listasDeCompra.addAll(dbHelper.obtenerListasCompra())

        // Notificar al adaptador que los datos han cambiado
        listaCompraAdapter.notifyDataSetChanged()
    }

    fun irAProfile(view: View) {
        val intent = Intent(this, Profile::class.java)
        startActivity(intent)
    }
    fun irANavigationMenu(view: View) {
        val intent = Intent(this, NavegationMenu::class.java)
        startActivity(intent)
    }

    fun irASaved(view: View) {
        val intent = Intent(this, SavedActivity::class.java)
        startActivity(intent)
    }
    fun irAShareList(view: View) {
        val intent = Intent(this, ShareList::class.java)
        startActivity(intent)
    }
    fun irAFilter(view: View) {
        val intent = Intent(this, Filter::class.java)
        startActivity(intent)
    }
}