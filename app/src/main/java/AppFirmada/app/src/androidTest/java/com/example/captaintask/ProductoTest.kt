package com.example.captaintask

import org.junit.Assert
import org.junit.Test

class ProductoTest {

    @Test
    fun testProductoConstructor() {
        val producto = Producto(null, "Arroz", "Descripción del Producto 1", 0)
        Assert.assertEquals("Arroz", producto.titulo)
        Assert.assertEquals("Descripción del Producto 1", producto.descripcion)
        Assert.assertEquals(0, producto.contador)
    }

    @Test
    fun testProductoCantidad() {
        val producto = Producto(null, "Arroz", "Descripción del Producto 1", 0)
        producto.aumentarCantidad(5)
        Assert.assertEquals(5, producto.contador)
        producto.disminuirCantidad(2)
        Assert.assertEquals(3, producto.contador)
    }
}