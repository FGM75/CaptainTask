package com.example.captaintask
import DatabaseHelper
import ProductoAdapter
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.FileOutputStream
import java.io.IOException



class SearchProducts : AppCompatActivity(), ProductoAdapter.ProductoClickListener {

    companion object {
        const val CODIGO_SELECCIONAR_IMAGEN = 1
        const val REQUEST_CODE_ACTUALIZAR_LISTA_COMPRA = 1
    }


    private lateinit var productoAdapter: ProductoAdapter
    private lateinit var recyclerView: RecyclerView
    private val productos = mutableListOf<Producto>()
    private lateinit var imageButton: ImageButton

    private lateinit var productosDB: List<Producto>

    private var searchText: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_products)
        // Obtener el texto enviado desde la actividad anterior
        searchText = intent.getStringExtra("searchText")

        // Verificar si searchText se recibió correctamente
        Log.d("SearchProducts", "searchText recibido: $searchText")

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        productoAdapter = ProductoAdapter(this, productos, this)
        recyclerView.adapter = productoAdapter
        imageButton=findViewById(R.id.cargarProductos)
        //se define aca afuera para poderse usar despues aunque me equivoque y no necesito usarla en otra clase
        val textProducto=findViewById<TextView>(R.id.textViewCantidadProductos)

        // Obtener el texto enviado desde la actividad anterior
        searchText = intent.getStringExtra("searchText")


        // Cargar los productos desde la base de datos y actualizar el RecyclerView
        val dbHelper = DatabaseHelper(this)

        // Insertar los productos iniciales si no existen en la lista
        insertarProductoSiNoExiste(dbHelper, Producto(Uri.parse("android.resource://${packageName}/${R.drawable.arroz}"), "Arroz", "Descripción del Producto 1", 0))
        insertarProductoSiNoExiste(dbHelper, Producto(Uri.parse("android.resource://${packageName}/${R.drawable.pasta}"), "Pasta", "Descripción del Producto 2", 0))
        insertarProductoSiNoExiste(dbHelper, Producto(Uri.parse("android.resource://${packageName}/${R.drawable.agua}"), "Agua", "Descripción del Producto 3", 0))

        // Obtener la lista actualizada de productos desde la base de datos
        productosDB = dbHelper.getProductos()

        // Verificar si cada producto de la base de datos ya está en la lista
        for (productoDB in productosDB) {
            if (!productos.any { it.titulo.lowercase() == productoDB.titulo.lowercase() }) {
                // El producto no está en la lista, así que lo agregamos
                productos.add(productoDB)
            }
        }

        // Actualizar el RecyclerView con la lista de productos
        productoAdapter.notifyDataSetChanged()

        //le paso a otra variable la cantidad de productos que hay en el List
        val cantidadProducto=productosDB.size
        //muestro en el textView con la longitud del list de productos
        textProducto.text="$cantidadProducto resultados encontrados"
    }

    private fun obtenerRutaImagen(idDrawable: Int): String {
        return "android.resource://${packageName}/drawable/${resources.getResourceEntryName(idDrawable)}"
    }

    private fun insertarProductoSiNoExiste(dbHelper: DatabaseHelper, producto: Producto) {
        if (!productos.any { it.titulo.lowercase() == producto.titulo.lowercase() }) {
            dbHelper.insertProducto(producto)
        }
    }

    override fun onIncrementarClick(producto: Producto) {
        // Implementa el comportamiento al hacer clic en el botón de incrementar

    }

    override fun onDisminuirClick(producto: Producto) {
        // Implementa el comportamiento al hacer clic en el botón de disminuir


    }

    // Métodos para cambiar de pantalla (código omitido por brevedad)

    fun irAAddObject(view: View) {
        val intent = Intent(this, AddObject::class.java)
        startActivity(intent)
    }

    // Método para manejar la selección de una imagen desde la galería
    fun seleccionarImagen(view: View) {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, CODIGO_SELECCIONAR_IMAGEN)
    }

    // Método para manejar el resultado de la selección de imagen
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CODIGO_SELECCIONAR_IMAGEN && resultCode == Activity.RESULT_OK) {
            // Se obtiene la URI de la imagen seleccionada
            val imagenUri = data?.data
            // Llama al método agregarProducto con la URI de la imagen seleccionada
            agregarProductoDesdeGaleria(imagenUri)
        }
    }

    // Método para agregar un producto con la imagen seleccionada desde la galería
    private fun agregarProductoDesdeGaleria(imagenUri: Uri?) {
        val intent = Intent(this, AddObject::class.java)
        intent.putExtra("imagenUri", imagenUri.toString())
        startActivity(intent)
    }

    fun obtenerLongitudProductosDB(): Int {
        return productosDB.size
    }

    fun agregarProducto(imagenUri: Uri?) {
        val nombreEditText = findViewById<EditText>(R.id.editText1)
        val descripcionEditText = findViewById<EditText>(R.id.editText2)

        val nombreProducto = nombreEditText.text.toString()
        val descripcionProducto = descripcionEditText.text.toString()

        if (nombreProducto.isNotEmpty() && descripcionProducto.isNotEmpty() && imagenUri != null) {
            val imagenId = guardarImagenEnAlmacenamiento(imagenUri)

            if (imagenId != null) {
                val dbHelper = DatabaseHelper(this)
                val imagenUri: Uri? = Uri.parse(imagenId)
                val producto = Producto(imagenUri, nombreProducto, descripcionProducto, 0)

                dbHelper.insertProducto(producto)

                nombreEditText.text.clear()
                descripcionEditText.text.clear()

                Toast.makeText(this, "Producto agregado correctamente", Toast.LENGTH_SHORT).show()

                // Devolver a SearchProducts
                val intent = Intent(this, SearchProducts::class.java)
                startActivity(intent)
                finish() // Finalizar la actividad actual para evitar la pila de actividades
            } else {
                // Mostrar un mensaje de error si hay un problema al guardar la imagen
                Toast.makeText(this, "Error al guardar la imagen", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Mostrar un mensaje de error si los campos están vacíos o la imagen no se selecciona
            Toast.makeText(this, "Por favor, complete todos los campos y seleccione una imagen", Toast.LENGTH_SHORT).show()
        }
    }

    private fun guardarImagenEnAlmacenamiento(imagenUri: Uri): String? {
        val inputStream = contentResolver.openInputStream(imagenUri) ?: return null
        val fileName = "${System.currentTimeMillis()}.jpg"
        val outputFile = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName)
        try {
            if (!outputFile.exists()) {
                outputFile.createNewFile()
            }
            FileOutputStream(outputFile).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
            return outputFile.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        } finally {
            inputStream.close()
        }
    }

    /*fun cargarProductos(view: View) {
        // verifica que searchText no sea nulo
        searchText?.let { texto ->
        // Obtener solo los productos con contador mayor que 0
        val productosSeleccionados = obtenerProductosSeleccionados().filter { it.contador > 0 }

        // Verificar si hay al menos un producto seleccionado


        if (productosSeleccionados.isNotEmpty()) {
            // Crear la ListaCompra con los productos seleccionados

            val listaCompra = ListaCompra(texto, productosSeleccionados.toMutableList())

            // Guardar la ListaCompra en la base de datos
            val dbHelper = DatabaseHelper(view.context)
            dbHelper.guardarListaCompra(listaCompra)

            // Obtener las ListasCompra desde la base de datos
            val listasDeCompra = dbHelper.obtenerListasCompra()

            // Luego, iniciar la otra actividad con startActivityForResult
            val intent = Intent(view.context, SavedActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_ACTUALIZAR_LISTA_COMPRA)
        } else {
            // Mostrar un mensaje al usuario indicando que no hay productos seleccionados
            Toast.makeText(view.context, "No hay productos seleccionados para añadir a la lista de compra", Toast.LENGTH_SHORT).show()
        }
    }
        }*/
    fun cargarProductos(view: View) {
        // verifica que searchText no sea nulo
        searchText?.let { texto ->
            Log.d("SearchProducts", "Texto recibido para la lista de compra: $texto")

            // Obtener solo los productos con contador mayor que 0
            val productosSeleccionados = obtenerProductosSeleccionados().filter { it.contador > 0 }

            // Verificar si hay al menos un producto seleccionado
            if (productosSeleccionados.isNotEmpty()) {
                Log.d("SearchProducts", "Productos seleccionados para la lista de compra: $productosSeleccionados")

                // Crear la ListaCompra con los productos seleccionados
                val listaCompra = ListaCompra(texto, productosSeleccionados.toMutableList())
                Log.d("SearchProducts", "Lista de compra creada: $listaCompra")

                // Guardar la ListaCompra en la base de datos
                val dbHelper = DatabaseHelper(view.context)
                dbHelper.guardarListaCompra(listaCompra)

                // Obtener las ListasCompra desde la base de datos
                val listasDeCompra = dbHelper.obtenerListasCompra()

                // Luego, iniciar la otra actividad con startActivityForResult
                val intent = Intent(view.context, SavedActivity::class.java)
                startActivityForResult(intent, REQUEST_CODE_ACTUALIZAR_LISTA_COMPRA)
            } else {
                // Mostrar un mensaje al usuario indicando que no hay productos seleccionados
                Toast.makeText(view.context, "No hay productos seleccionados para añadir a la lista de compra", Toast.LENGTH_SHORT).show()
            }
        }
    }



    // Método para obtener los productos que están en la lista de compra
    private fun obtenerProductosSeleccionados(): MutableList<Producto> {
        return productos.filter { it.contador > 0 }.toMutableList()
    }




}
