package com.example.captaintask

data class Producto(val imagen: Int, val titulo: String, val descripcion: String, var contador: Int){
    // Sobrescribir la función equals para comparar dos productos por nombre ignorando el caso
    override fun equals(other: Any?): Boolean {
        // Verificar si el objeto es nulo o no es un Producto
        if (this === other) return true
        if (other !is Producto) return false

        // Convertir los nombres a minúsculas y compararlos
        val thisNombre = this.titulo.toLowerCase()
        val otherNombre = other.titulo.toLowerCase()
        return thisNombre == otherNombre
    }

    // Sobrescribir la función hashCode para asegurar la consistencia con la función equals
    override fun hashCode(): Int {
        return titulo.toLowerCase().hashCode()
    }
}

