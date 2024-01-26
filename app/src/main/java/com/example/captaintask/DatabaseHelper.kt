package com.example.captaintask

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
                "(id INTEGER PRIMARY KEY, correo TEXT, contraseña TEXT)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_USERS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Aquí puedes manejar actualizaciones de la base de datos si es necesario
    }

    fun insertUsuario(correo: String, contraseña: String): Long {
        val db = writableDatabase

        val values = ContentValues().apply {
            put("correo", correo)
            put("contraseña", contraseña)
        }

        return db.insert("usuarios", null, values)
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
}
