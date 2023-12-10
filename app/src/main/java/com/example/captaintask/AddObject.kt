package com.example.captaintask

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class AddObject : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_object)

    }
    fun irAPersonalInformation(view: View) {
        val intent = Intent(this, PersonalInformation::class.java)
        startActivity(intent)
    }
    fun irANavigationMenu(view: View) {
        val intent = Intent(this, NavegationMenu::class.java)
        startActivity(intent)
    }
    fun irASavedActivity(view: View) {
        val intent = Intent(this, SavedActivity::class.java)
        startActivity(intent)
    }
    fun irASearchProducts(view: View) {
        val intent = Intent(this, SearchProducts::class.java)
        startActivity(intent)
    }




}