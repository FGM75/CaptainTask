package com.example.captaintask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.example.captaintask.SavedActivity


class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var textViewForgotPassword: TextView
    private lateinit var goToRegisterButton: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        dbHelper = DatabaseHelper(this)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        textViewForgotPassword = findViewById(R.id.textViewForgotPassword)
        goToRegisterButton = findViewById(R.id.goToRegisterButton)

        loginButton.setOnClickListener {
            val correo = emailEditText.text.toString()
            val contraseña = passwordEditText.text.toString()

            if (dbHelper.checkUsuario(correo, contraseña)) {
                // Usuario válido, iniciar la siguiente actividad
                startActivity(Intent(this, SavedActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectas", Toast.LENGTH_SHORT).show()
            }
        }

        goToRegisterButton.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }

        textViewForgotPassword.setOnClickListener {
            // Agrega aquí la lógica para manejar la recuperación de contraseña
            startActivity(Intent(this, RecoverPassword::class.java))
            // Puedes abrir otra actividad o mostrar un cuadro de diálogo, por ejemplo.
        }
    }












    //fun irASavedActivity(view: View) {
        // Crear un Intent para iniciar SavedActivity
      //  val intent = Intent(this, SavedActivity::class.java)
        //startActivity(intent)
    //}

  //  fun irARecuContra(view: View) {
    //    val intent = Intent(this, RecoverPassword::class.java)
    //    startActivity(intent)
    //   }

    //   fun irARegister(view: View) {
    //   val intent = Intent(this, Register::class.java)
    //   startActivity(intent)
    //  }


}