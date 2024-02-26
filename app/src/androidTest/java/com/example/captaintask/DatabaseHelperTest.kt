package com.example.captaintask






import DatabaseHelper
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseHelperTest {

    private lateinit var dbHelper: DatabaseHelper

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        dbHelper = DatabaseHelper(context)
    }

    @Test
    fun testInsertProducto() {
        val producto = Producto(null, "aguacate", "Descripción del Producto 2", 0)
        val id = dbHelper.insertProducto(producto)
        assertNotEquals(-1L, id)
    }

    @Test
    fun testExisteProducto() {
        val producto = Producto(null, "Arroz", "Descripción del Producto 1", 0)
        dbHelper.insertProducto(producto)

        assertTrue(dbHelper.existeProducto("Arroz"))
        assertFalse(dbHelper.existeProducto("Pasta"))
    }
}



