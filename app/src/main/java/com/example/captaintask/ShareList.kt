package com.example.captaintask



import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ShareList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.share_list)

        val textUser = findViewById<TextView>(R.id.TextUser)

        // Obtener el correo electrónico almacenado en MainActivity
        val user = MainActivity.correoUser

        // Verificar si se ha almacenado un correo electrónico
        if (user.isNotEmpty()){
            // Establecer el texto en el TextView
            textUser.text = "Hola $user, ¿te gustaría compartir tu lista?"

        }
    }
    fun irANavigationMenu(view: View) {
        val intent = Intent(this, NavegationMenu::class.java)
        startActivity(intent)
    }

    fun irASaved(view: View) {
        val intent = Intent(this, SavedActivity::class.java)
        startActivity(intent)
    }

    fun irAFilter(view: View) {
        val intent = Intent(this, Filter::class.java)
        startActivity(intent)
    }
    fun irAProfile(view: View) {
        val intent = Intent(this, Profile::class.java)
        startActivity(intent)
    }
    fun irAShareList(view: View) {
        val intent = Intent(this, ShareList::class.java)
        startActivity(intent)
    }
    fun irASearchProducts(view: View) {
        val intent = Intent(this, SearchProducts::class.java)
        startActivity(intent)
    }



}