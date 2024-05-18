package com.example.captaintask

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class NavegationMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_menu)

    }

    fun irALegalActivity(view: View) {
        // Crear un Intent para iniciar SavedActivity
        val intent = Intent(this, LegalTerms::class.java)
        startActivity(intent)
    }

    fun irAContactoActivity(view: View) {
        // Crear un Intent para iniciar SavedActivity
        val intent = Intent(this, Contact::class.java)
        startActivity(intent)
    }

    fun irAMainActivity(view: View) {
        // Crear un Intent para iniciar SavedActivity
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun irAReportActivity(view: View) {
        // Crear un Intent para iniciar SavedActivity
        val intent = Intent(this, ReportActivity::class.java)
        startActivity(intent)
    }


}