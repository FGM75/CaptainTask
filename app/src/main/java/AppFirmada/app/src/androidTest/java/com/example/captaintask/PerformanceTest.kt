package com.example.captaintask

import DatabaseHelper
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PerformanceTest {

    private lateinit var dbHelper: DatabaseHelper

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        dbHelper = DatabaseHelper(context)
        // Agregar algunos productos a la base de datos para la prueba
        val producto1 = Producto(null, "Leche", "Descripción de la leche", 0)
        val producto2 = Producto(null, "Pan", "Descripción del pan", 0)
        dbHelper.insertProducto(producto1)
        dbHelper.insertProducto(producto2)
    }

    @Test
    fun testProductListLoadPerformance() {
        // Simulación de carga de la lista de productos (solo para fines de demostración)
        val startTime = System.currentTimeMillis()
        val productos = dbHelper.getProductos() // Obtener productos de la base de datos
        val endTime = System.currentTimeMillis()

        // Verificar que se obtuvieron productos de la base de datos
        assertTrue(productos.isNotEmpty())

        // Calcular el tiempo de carga de la lista de productos
        val elapsedTime = endTime - startTime

        // Establecer un umbral de tiempo aceptable para la carga de la lista de productos
        val acceptableTime = 1000 // 1000 milisegundos = 1 segundo
        assertTrue("El tiempo de carga de la lista de productos ($elapsedTime ms) supera el tiempo aceptable ($acceptableTime ms)",
            elapsedTime < acceptableTime)
    }
}
