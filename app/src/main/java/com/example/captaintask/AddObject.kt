package com.example.captaintask
import DatabaseHelper
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.internal.NavigationMenu
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class AddObject : AppCompatActivity() {

    private lateinit var nombreEditText: EditText
    private lateinit var descripcionEditText: EditText
    private lateinit var imageView: ImageView
    private var imagenUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_object)

        nombreEditText = findViewById(R.id.editText1)
        descripcionEditText = findViewById(R.id.editText2)
        imageView = findViewById(R.id.imagen)

        imageView.setOnClickListener {
            // Manejar la selección de una imagen desde la galería
            seleccionarImagen()
        }

        val botonAgregar = findViewById<Button>(R.id.boton)
        botonAgregar.setOnClickListener {
            // Manejar el clic del botón "Agregar"
            agregarProducto()
        }
    }

    private fun seleccionarImagen() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, SearchProducts.CODIGO_SELECCIONAR_IMAGEN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SearchProducts.CODIGO_SELECCIONAR_IMAGEN && resultCode == Activity.RESULT_OK) {
            // Obtener la URI de la imagen seleccionada
            imagenUri = data?.data
            // Actualizar la ImageView con la imagen seleccionada
            imageView.setImageURI(imagenUri)
        }
    }

    private fun agregarProducto() {
        val nombre = nombreEditText.text.toString()
        val descripcion = descripcionEditText.text.toString()

        if (nombre.isNotEmpty() && descripcion.isNotEmpty() && imagenUri != null) {
            val dbHelper = DatabaseHelper(this)

            // Verificar si ya existe un producto con el mismo nombre
            if (dbHelper.existeProducto(dbHelper,nombre)) {
                Toast.makeText(this, "Ya existe un producto con el mismo nombre", Toast.LENGTH_SHORT).show()
                return
            }

            val producto = Producto(imagenUri, nombre, descripcion, 0)

            // Intentar insertar el producto en la base de datos
            val exitoInsercion = dbHelper.insertProducto(producto)

            if (exitoInsercion != -1L) {
                // Mostrar un mensaje de éxito
                Toast.makeText(this, "Producto agregado correctamente", Toast.LENGTH_SHORT).show()

                // Limpiar los campos de texto e imagen
                nombreEditText.text.clear()
                descripcionEditText.text.clear()
                imageView.setImageResource(R.drawable.baseline_add_photo_alternate_black_48) // Restaurar la imagen predeterminada

                // Redirigir a SearchProducts
                val intent = Intent(this, SearchProducts::class.java)
                startActivity(intent)
            } else {
                // Mostrar un mensaje de error si la inserción falló
                Toast.makeText(this, "Error al agregar el producto", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Mostrar un mensaje de error si algún campo está vacío o no se seleccionó ninguna imagen
            Toast.makeText(this, "Por favor, complete todos los campos y seleccione una imagen", Toast.LENGTH_SHORT).show()
        }
    }



}
