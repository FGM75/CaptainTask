package com.example.captaintask

import DatabaseHelper
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class Register : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var passwordMessageTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        dbHelper = DatabaseHelper(this)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        registerButton = findViewById(R.id.loginButton)
        passwordMessageTextView = findViewById(R.id.passwordMessage)

        registerButton.setOnClickListener {
            val correo = emailEditText.text.toString()
            val contraseña = passwordEditText.text.toString()

            if (correo.isNotEmpty() && contraseña.isNotEmpty()) {
                if (validarContraseña(contraseña)) {
                    val id = dbHelper.insertUsuario(correo, contraseña)

                    if (id > 0) {
                        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else if (id == -1L) {
                        Toast.makeText(this, "El correo electrónico ya está registrado", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Error al registrar. Intenta nuevamente", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres, una mayúscula y una minúscula", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mostrarMensajesDeValidacion(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun validarContraseña(contraseña: String): Boolean {
        val regex = Regex("^(?=.*[a-z])(?=.*[A-Z]).{6,}$")
        return regex.matches(contraseña)
    }

    private fun mostrarMensajesDeValidacion(contraseña: String) {
        val mensajes = StringBuilder()
        if (!Regex(".*[a-z].*").containsMatchIn(contraseña)) {
            mensajes.append("Debe contener al menos una letra minúscula.\n")
        }
        if (!Regex(".*[A-Z].*").containsMatchIn(contraseña)) {
            mensajes.append("Debe contener al menos una letra mayúscula.\n")
        }
        if (contraseña.length < 6) {
            mensajes.append("Debe tener al menos 6 caracteres.\n")
        }

        if (mensajes.isEmpty()) {
            passwordMessageTextView.visibility = TextView.GONE
        } else {
            passwordMessageTextView.visibility = TextView.VISIBLE
            passwordMessageTextView.text = mensajes.toString()
        }
    }
}

