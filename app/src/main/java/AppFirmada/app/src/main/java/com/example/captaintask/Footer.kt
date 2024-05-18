package com.example.captaintask

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import java.util.logging.Filter

class Footer : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.footer_layout)

    }

    fun irAShareList(view: View) {
        val intent = Intent(this, ShareList::class.java)
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
}