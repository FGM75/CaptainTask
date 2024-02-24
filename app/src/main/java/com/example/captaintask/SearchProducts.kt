package com.example.captaintask
import DatabaseHelper
import ProductoAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.internal.NavigationMenu

class SearchProducts : AppCompatActivity(), ProductoAdapter.ProductoClickListener {

    private lateinit var productoAdapter: ProductoAdapter
    private lateinit var recyclerView: RecyclerView
    private val productos = mutableListOf<Producto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_products)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        productoAdapter = ProductoAdapter(this, productos, this)
        recyclerView.adapter = productoAdapter

        // Cargar los productos desde la base de datos y actualizar el RecyclerView
        val dbHelper = DatabaseHelper(this)

        // Insertar los productos iniciales si no existen en la lista
        insertarProductoSiNoExiste(dbHelper, Producto(R.drawable.arroz, "Arroz", "Descripción del Producto 1", 0))
        insertarProductoSiNoExiste(dbHelper, Producto(R.drawable.pasta, "Pasta", "Descripción del Producto 2", 0))
        insertarProductoSiNoExiste(dbHelper, Producto(R.drawable.agua, "Agua", "Descripción del Producto 3", 0))

        // Obtener la lista actualizada de productos desde la base de datos
        val productosDB = dbHelper.getProductos()

        // Verificar si cada producto de la base de datos ya está en la lista
        for (productoDB in productosDB) {
            if (!productos.any { it.titulo.toLowerCase() == productoDB.titulo.toLowerCase() }) {
                // El producto no está en la lista, así que lo agregamos
                productos.add(productoDB)
            }
        }

        // Actualizar el RecyclerView con la lista de productos
        productoAdapter.notifyDataSetChanged()
    }

    private fun insertarProductoSiNoExiste(dbHelper: DatabaseHelper, producto: Producto) {
        if (!productos.any { it.titulo.toLowerCase() == producto.titulo.toLowerCase() }) {
            dbHelper.insertProducto(producto)
        }
    }

    override fun onIncrementarClick(producto: Producto) {
        // Implementa el comportamiento al hacer clic en el botón de incrementar
    }

    override fun onDisminuirClick(producto: Producto) {
        // Implementa el comportamiento al hacer clic en el botón de disminuir
    }

    // Métodos para cambiar de pantalla
    fun irAProfile(view: View) {
        val intent = Intent(this, Profile::class.java)
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
