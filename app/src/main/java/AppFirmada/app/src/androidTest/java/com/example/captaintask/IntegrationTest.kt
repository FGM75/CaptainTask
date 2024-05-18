package com.example.captaintask

import DatabaseHelper
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class IntegrationTest {

    private lateinit var dbHelper: DatabaseHelper

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        dbHelper = DatabaseHelper(context)
    }

    @Test
    fun testInsertAndGetProductos() {
        val producto1 = Producto(null, "leche", "Descripción del Producto 3", 0)
        val producto2 = Producto(null, "pan", "Descripción del Producto 4", 0)

        val idProducto1 = dbHelper.insertProducto(producto1)
        val idProducto2 = dbHelper.insertProducto(producto2)

        val productos = dbHelper.getProductos()

        assertEquals(2, productos.size)
        assertEquals("leche", productos[0].titulo)
        assertEquals("Descripción del Producto 3", productos[0].descripcion)
        assertEquals("pan", productos[1].titulo)
        assertEquals("Descripción del Producto 4", productos[1].descripcion)
    }
}
