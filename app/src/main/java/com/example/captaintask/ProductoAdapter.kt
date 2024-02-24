import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.captaintask.Producto
import com.example.captaintask.R

class ProductoAdapter(private val context: Context, private val productos: MutableList<Producto>, private val listener: ProductoClickListener) :
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

        // Cargar la imagen del producto utilizando Glide u otra biblioteca de carga de imágenes
        Glide.with(context)
            .load(producto.imagen)
            .into(holder.imageViewProducto)

        holder.textViewTitulo.text = producto.titulo
        holder.textViewDescripcion.text = producto.descripcion
        holder.textViewContador.text = producto.contador.toString()

        // Configurar clics de los botones
        holder.btnIncrementar.setOnClickListener {
            producto.contador++
            holder.textViewContador.text = producto.contador.toString()
            listener.onIncrementarClick(producto)
        }

        holder.btnDisminuir.setOnClickListener {
            if (producto.contador > 0) {
                producto.contador--
                holder.textViewContador.text = producto.contador.toString()
                listener.onDisminuirClick(producto)
            }
        }
    }

    override fun getItemCount(): Int {
        return productos.size
    }

    // Interfaz para manejar eventos de clic en los botones
    interface ProductoClickListener {
        fun onIncrementarClick(producto: Producto)
        fun onDisminuirClick(producto: Producto)
    }

    // Método para actualizar la lista de productos
    fun actualizarProductos(nuevosProductos: List<Producto>) {
        productos.clear()
        productos.addAll(nuevosProductos)
        notifyDataSetChanged()
    }
}
