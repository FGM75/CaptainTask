import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "captaintask.db"
        private const val DATABASE_VERSION = 1

        private const val CREATE_TABLE_USERS = "CREATE TABLE IF NOT EXISTS usuarios " +
                "(id INTEGER PRIMARY KEY, correo TEXT UNIQUE, contraseña TEXT)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_USERS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Aquí puedes manejar actualizaciones de la base de datos si es necesario
    }

    fun insertUsuario(correo: String, contraseña: String): Long {
        val db = writableDatabase

        // Verificar si la contraseña cumple con los requisitos mínimos
        if (!contraseñaCumpleRequisitos(contraseña)) {
            // La contraseña no cumple los requisitos mínimos
            return -1
        }

        val values = ContentValues().apply {
            put("correo", correo)
            put("contraseña", contraseña)
        }

        // Insertar el usuario en la base de datos
        return try {
            db.insertOrThrow("usuarios", null, values)
        } catch (e: Exception) {
            // Error al insertar debido a un correo duplicado
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

    private fun contraseñaCumpleRequisitos(contraseña: String): Boolean {
        // Verificar si la contraseña tiene al menos 6 caracteres,
        // una mayúscula y una minúscula
        val regex = "^(?=.*[a-z])(?=.*[A-Z]).{6,}$".toRegex()
        return contraseña.matches(regex)
    }
}
