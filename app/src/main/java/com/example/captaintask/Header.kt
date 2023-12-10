package com.example.captaintask

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class Header : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.header_layout)

    }
    fun irAPersonalInformation(view: View) {
        val intent = Intent(this, PersonalInformation::class.java)
        startActivity(intent)
    }
    fun irANavigationMenu(view: View) {
        val intent = Intent(this, NavegationMenu::class.java)
        startActivity(intent)
    }

}