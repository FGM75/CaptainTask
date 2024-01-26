package com.example.captaintask


import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

import android.widget.Toast

class Register : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        dbHelper = DatabaseHelper(this)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        registerButton = findViewById(R.id.loginButton)

        registerButton.setOnClickListener {
            val correo = emailEditText.text.toString()
            val contraseña = passwordEditText.text.toString()

            // Verificar si el correo y la contraseña no están vacíos
            if (correo.isNotEmpty() && contraseña.isNotEmpty()) {
                // Insertar usuario en la base de datos
                val id = dbHelper.insertUsuario(correo, contraseña)

                if (id > 0) {
                    // Registro exitoso, redirigir a MainActivity
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Error al registrar. Intenta nuevamente", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }



   // fun irASavedActivity(view: View) {
        // Crear un Intent para iniciar SavedActivity
    //   val intent = Intent(this, SavedActivity::class.java)
    //  startActivity(intent)
    //}
}