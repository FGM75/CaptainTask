package com.example.captaintask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class ListaCompraAdapter(private val listasCompras: List<ListaCompra>) :
    RecyclerView.Adapter<ListaCompraAdapter.ViewHolder>(), Filterable {

    private var listasComprasFiltradas: List<ListaCompra> = listasCompras

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lista_compra, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listaCompra = listasComprasFiltradas[position]

        //esto coge el nombre que le coloca el usuario
        holder.itemView.findViewById<TextView>(R.id.textViewNombreListaCompra).text = listaCompra.nombre
        // Configura tus vistas con los datos de listaCompra
    }

    override fun getItemCount(): Int {
        return listasComprasFiltradas.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Configura tus vistas aquí
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString().toLowerCase(Locale.getDefault())
                val filteredList = if (charSearch.isEmpty()) {
                    listasCompras
                } else {
                    val resultList = mutableListOf<ListaCompra>()
                    for (listaCompra in listasCompras) {
                        if (listaCompra.nombre.toLowerCase(Locale.getDefault()).contains(charSearch)) {
                            resultList.add(listaCompra)
                        }
                    }
                    resultList
                }

                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }
            fun updateList(newList: List<ListaCompra>) {
                listasComprasFiltradas = newList.toMutableList() // Convierte la lista a una lista mutable
                notifyDataSetChanged() // Notificar al adaptador que los datos han cambiado
            }


            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                listasComprasFiltradas = results?.values as List<ListaCompra>
                notifyDataSetChanged()
            }
        }
    }
}
