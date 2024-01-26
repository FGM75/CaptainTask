package com.example.captaintask

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Filter : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.filter)

        val listasCompras = listOf(
            ListaCompra("Lista 1", R.drawable.baseline_edit_square_black_20),
            ListaCompra("Lista 2", R.drawable.baseline_edit_square_black_20),
            ListaCompra("Lista 3", R.drawable.baseline_edit_square_black_20),
            ListaCompra("Lista 4", R.drawable.baseline_edit_square_black_20),
            ListaCompra("Lista 5", R.drawable.baseline_edit_square_black_20),
            ListaCompra("Lista 6", R.drawable.baseline_edit_square_black_20),
            ListaCompra("Lista 7", R.drawable.baseline_edit_square_black_20),
            // Agrega más listas según sea necesario
        )

        val recyclerView = findViewById<RecyclerView>(R.id.tuRecyclerView)
        recyclerView.adapter = ListaCompraAdapter(listasCompras)
        recyclerView.layoutManager = GridLayoutManager(this, 2)


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