package com.example.captaintask

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
    }
    fun irASavedActivity(view: View) {
        // Crear un Intent para iniciar SavedActivity
        val intent = Intent(this, SavedActivity::class.java)
        startActivity(intent)
    }
}