import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.captaintask.Producto


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "captaintask.db"
        private const val DATABASE_VERSION = 1

        private const val CREATE_TABLE_USERS = "CREATE TABLE IF NOT EXISTS usuarios " +
                "(id INTEGER PRIMARY KEY, correo TEXT UNIQUE, contraseña TEXT)"

        private const val CREATE_TABLE_PRODUCTS = "CREATE TABLE IF NOT EXISTS productos " +
                "(id INTEGER PRIMARY KEY, imagen INTEGER, titulo TEXT, descripcion TEXT, contador INTEGER)"


    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_USERS)
        db.execSQL(CREATE_TABLE_PRODUCTS)
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
        val cursor = db.rawQuery("SELECT 1 FROM productos WHERE imagen = ? AND titulo = ? AND descripcion = ? AND contador = ?", arrayOf(producto.imagen.toString(), producto.titulo, producto.descripcion, producto.contador.toString()))

        // Si el cursor tiene al menos un resultado, significa que el producto ya existe
        if (cursor.count > 0) {
            cursor.close()
            return -1 // Devuelve -1 para indicar que el producto ya existe
        }

        cursor.close()

        // Si el producto no existe, procede a insertarlo
        val values = ContentValues().apply {
            put("imagen", producto.imagen)
            put("titulo", producto.titulo)
            put("descripcion", producto.descripcion)
            put("contador", producto.contador)
        }

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
                val imagen = cursor.getInt(imagenIndex)
                val titulo = cursor.getString(tituloIndex)
                val descripcion = cursor.getString(descripcionIndex)
                val contador = cursor.getInt(contadorIndex)
                val producto = Producto(imagen, titulo, descripcion, contador)
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
}
