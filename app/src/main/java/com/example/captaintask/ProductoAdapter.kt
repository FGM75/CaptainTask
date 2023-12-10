package com.example.captaintask

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductoAdapter(private val productos: List<Producto>) :
    RecyclerView.Adapter<ProductoAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewProducto: ImageView = itemView.findViewById(R.id.imageViewProducto)
        val textViewTitulo: TextView = itemView.findViewById(R.id.textViewTitulo)
        val textViewDescripcion: TextView = itemView.findViewById(R.id.textViewDescripcion)
        val textViewContador: TextView = itemView.findViewById(R.id.textViewContador)
        val btnIncrementar: ImageButton = itemView.findViewById(R.id.btnIncrementar)
        val btnDisminuir: ImageButton = itemView.findViewById(R.id.btnDisminuir)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_producto, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = productos[position]

        holder.imageViewProducto.setImageResource(producto.imagen)
        holder.textViewTitulo.text = producto.titulo
        holder.textViewDescripcion.text = producto.descripcion
        holder.textViewContador.text = producto.contador.toString()

        // Configurar clics de los botones
        holder.btnIncrementar.setOnClickListener {
            producto.contador++
            holder.textViewContador.text = producto.contador.toString()
        }

        holder.btnDisminuir.setOnClickListener {
            if (producto.contador > 0) {
                producto.contador--
                holder.textViewContador.text = producto.contador.toString()
            }
        }
    }

    override fun getItemCount(): Int {
        return productos.size
    }
}
