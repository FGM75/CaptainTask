package com.example.captaintask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.captaintask.SavedActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun irASavedActivity(view: View) {
        // Crear un Intent para iniciar SavedActivity
        val intent = Intent(this, SavedActivity::class.java)
        startActivity(intent)
    }

    fun irARecuContra(view: View) {
        val intent = Intent(this, RecoverPassword::class.java)
        startActivity(intent)
    }

    fun irARegister(view: View) {
        val intent = Intent(this, Register::class.java)
        startActivity(intent)
    }


}