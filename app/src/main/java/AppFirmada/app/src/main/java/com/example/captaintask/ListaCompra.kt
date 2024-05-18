package com.example.captaintask

data class ListaCompra(val nombre: String, val productos: MutableList<Producto> = mutableListOf())
