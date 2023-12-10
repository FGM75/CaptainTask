package com.example.captaintask

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.logging.Filter

class SearchProducts : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_products)

        val productos = listOf(
            Producto(R.drawable.baseline_add_photo_alternate_black_20, "Producto 1", "Descripción del Producto 1", 0),
            Producto(R.drawable.baseline_add_photo_alternate_black_20, "Producto 2", "Descripción del Producto 2", 0),
            Producto(R.drawable.baseline_add_photo_alternate_black_20, "Producto 3", "Descripción del Producto 3", 0),
            Producto(R.drawable.baseline_add_photo_alternate_black_20, "Producto 4", "Descripción del Producto 4", 0),
            Producto(R.drawable.baseline_add_photo_alternate_black_20, "Producto 5", "Descripción del Producto 5", 0),
            Producto(R.drawable.baseline_add_photo_alternate_black_20, "Producto 6", "Descripción del Producto 6", 0),
            Producto(R.drawable.baseline_add_photo_alternate_black_20, "Producto 7", "Descripción del Producto 7", 0),
            // Agrega más productos según sea necesario
        )

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val productoAdapter = ProductoAdapter(productos)
        recyclerView.adapter = productoAdapter

    }
    fun irAPersonalInformation(view: View) {
        val intent = Intent(this, PersonalInformation::class.java)
        startActivity(intent)
    }
    fun irANavigationMenu(view: View) {
        val intent = Intent(this, NavegationMenu::class.java)
        startActivity(intent)
    }
    
    fun irAFilter(view: View) {
        val intent = Intent(this, Filter::class.java)
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
    fun irAAddObject(view: View) {
        val intent = Intent(this, AddObject::class.java)
        startActivity(intent)
    }
}