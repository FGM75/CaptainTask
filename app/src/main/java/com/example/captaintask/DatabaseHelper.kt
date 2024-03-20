import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import android.util.Log
import com.example.captaintask.ListaCompra
import com.example.captaintask.Producto


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "captaintask.db"
        private const val DATABASE_VERSION = 1

        private const val CREATE_TABLE_USERS = "CREATE TABLE IF NOT EXISTS usuarios " +
                "(id INTEGER PRIMARY KEY, correo TEXT UNIQUE, contraseña TEXT)"

        private const val CREATE_TABLE_PRODUCTS = "CREATE TABLE IF NOT EXISTS productos " +
                "(id INTEGER PRIMARY KEY, imagen TEXT, titulo TEXT UNIQUE, descripcion TEXT, contador INTEGER)"


        private const val CREATE_TABLE_LISTAS = "CREATE TABLE IF NOT EXISTS listas " +
                "(id INTEGER PRIMARY KEY, nombre TEXT UNIQUE)"

        private const val CREATE_TABLE_LISTA_PRODUCTOS = "CREATE TABLE IF NOT EXISTS lista_productos " +
                "(id INTEGER PRIMARY KEY, lista_id INTEGER, producto_id INTEGER, cantidad INTEGER)"



    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_USERS)
        db.execSQL(CREATE_TABLE_PRODUCTS)
        db.execSQL(CREATE_TABLE_LISTAS)
        db.execSQL(CREATE_TABLE_LISTA_PRODUCTOS)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database upgrades here if necessary
    }

    fun insertUsuario(correo: String, contraseña: String): Long {
        val db = writableDatabase

        if (!contraseñaCumpleRequisitos(contraseña)) {
            return -1
        }

        val values = ContentValues().apply {
            put("correo", correo)
            put("contraseña", contraseña)
        }

        return try {
            db.insertOrThrow("usuarios", null, values)
        } catch (e: Exception) {
            -1
        }
    }

    fun checkUsuario(correo: String, contraseña: String): Boolean {
        val db = readableDatabase

        val selection = "correo = ? AND contraseña = ?"
        val selectionArgs = arrayOf(correo, contraseña)

        val cursor: Cursor = db.query("usuarios", null, selection, selectionArgs, null, null, null)

        val result = cursor.count > 0

        cursor.close()
        return result
    }

    fun insertProducto(producto: Producto): Long {
        val db = writableDatabase

        // Verificar si el producto ya existe en la base de datos
        val cursor = db.rawQuery("SELECT 1 FROM productos WHERE LOWER(titulo) = LOWER(?)", arrayOf(producto.titulo))

        // Si el cursor tiene al menos un resultado, significa que el producto ya existe
        if (cursor.count > 0) {
            cursor.close()
            return -1 // Devuelve -1 para indicar que el producto ya existe
        }

        cursor.close()

        // Si el producto no existe, procede a insertarlo
        val values = ContentValues().apply {
            put("titulo", producto.titulo)
            put("descripcion", producto.descripcion)
            put("contador", producto.contador)
        }

        // Verificar si la URI de la imagen no es nula antes de agregarla a los valores
        producto.imagenUri?.let { uri ->
            values.put("imagen", uri.toString()) // Convertir la URI a String
        }

        // Insertar el producto en la base de datos
        return db.insert("productos", null, values)
    }



    fun getProductos(): List<Producto> {
        val productos = mutableListOf<Producto>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM productos", null)

        while (cursor.moveToNext()) {
            val imagenIndex = cursor.getColumnIndex("imagen")
            val tituloIndex = cursor.getColumnIndex("titulo")
            val descripcionIndex = cursor.getColumnIndex("descripcion")
            val contadorIndex = cursor.getColumnIndex("contador")

            if (imagenIndex != -1 && tituloIndex != -1 && descripcionIndex != -1 && contadorIndex != -1) {
                val imagenUriString = cursor.getString(imagenIndex)
                val imagenUri = Uri.parse(imagenUriString) // Convertir la cadena de URI a URI
                val titulo = cursor.getString(tituloIndex)
                val descripcion = cursor.getString(descripcionIndex)
                val contador = cursor.getInt(contadorIndex)
                val producto = Producto(imagenUri, titulo, descripcion, contador)
                productos.add(producto)
            }
        }

        cursor.close()
        return productos
    }



    private fun contraseñaCumpleRequisitos(contraseña: String): Boolean {
        val regex = "^(?=.*[a-z])(?=.*[A-Z]).{6,}$".toRegex()
        return contraseña.matches(regex)
    }

    //funcion para verificar si el producto existe en la base de datos
    fun existeProducto( nombre: String): Boolean {
        val db = readableDatabase
        val query = "SELECT 1 FROM productos WHERE LOWER(titulo) = LOWER(?)"
        val cursor = db.rawQuery(query, arrayOf(nombre.toLowerCase()))
        val existe = cursor.count > 0
        cursor.close()
        return existe
    }

    // Agregar función para guardar una lista de compra en la base de datos
    fun guardarListaCompra(listaCompra: ListaCompra): Long {
        val db = writableDatabase

        // Agregar registros para depurar
        Log.d("DatabaseHelper", "Guardando lista de compra: $listaCompra")

        // Primero, insertar la lista de compra en la tabla "listas"
        val listaValues = ContentValues().apply {
            put("nombre", listaCompra.nombre)
        }
        val listaId = db.insert("listas", null, listaValues)

        // Luego, insertar cada producto de la lista de compra en la tabla "lista_productos"
        listaCompra.productos.forEach { producto ->
            val productoValues = ContentValues().apply {
                put("lista_id", listaId)
                put("producto_id", obtenerIdProducto(producto.titulo)) // Obtener el ID del producto desde la tabla "productos"
                put("cantidad", producto.contador)
            }
            db.insert("lista_productos", null, productoValues)
        }

        return listaId
    }

    // Función para obtener el ID de un producto dado su título
    private fun obtenerIdProducto(tituloProducto: String): Long {
        val db = readableDatabase
        val query = "SELECT id FROM productos WHERE LOWER(titulo) = LOWER(?)"
        val cursor = db.rawQuery(query, arrayOf(tituloProducto.toLowerCase()))

        val productoId = if (cursor != null && cursor.count > 0) {
            // La consulta devolvió resultados y el cursor no es nulo
            val idIndex = cursor.getColumnIndex("id")
            if (idIndex >= 0) {
                // La columna "id" existe en el cursor
                cursor.moveToFirst()
                cursor.getLong(idIndex)
            } else {
                // La columna "id" no está presente en el cursor
                cursor.close()
                -1
            }
        } else {
            // No se encontraron resultados o el cursor es nulo
            -1
        }

        cursor?.close()
        return productoId
    }
    fun obtenerListasCompra(): List<ListaCompra> {
        val listasCompra = mutableListOf<ListaCompra>()
        val db = readableDatabase
        val query = "SELECT * FROM listas"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val idIndex = cursor.getColumnIndex("id")
            val nombreIndex = cursor.getColumnIndex("nombre")

            if (idIndex != -1 && nombreIndex != -1) {
                val id = cursor.getLong(idIndex)
                val nombre = cursor.getString(nombreIndex)
                val productos = obtenerProductosDeLista(id)
                val listaCompra = ListaCompra(nombre, productos)
                listasCompra.add(listaCompra)
            }
        }

        cursor.close()
        return listasCompra
    }


    private fun obtenerProductosDeLista(idLista: Long): MutableList<Producto> {
        val productos = mutableListOf<Producto>()
        val db = readableDatabase
        val query = "SELECT p.* FROM productos p INNER JOIN lista_productos lp ON p.id = lp.producto_id WHERE lp.lista_id = ?"
        val cursor = db.rawQuery(query, arrayOf(idLista.toString()))

        while (cursor.moveToNext()) {
            val imagenIndex = cursor.getColumnIndex("imagen")
            val tituloIndex = cursor.getColumnIndex("titulo")
            val descripcionIndex = cursor.getColumnIndex("descripcion")
            val contadorIndex = cursor.getColumnIndex("contador")

            if (imagenIndex != -1 && tituloIndex != -1 && descripcionIndex != -1 && contadorIndex != -1) {
                val imagenUriString = cursor.getString(imagenIndex)
                val imagenUri = Uri.parse(imagenUriString)
                val titulo = cursor.getString(tituloIndex)
                val descripcion = cursor.getString(descripcionIndex)
                val contador = cursor.getInt(contadorIndex)
                val producto = Producto(imagenUri, titulo, descripcion, contador)
                productos.add(producto)
            }
        }

        cursor.close()
        return productos
    }








}
