package com.example.captaintask

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class RecoverPassword : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recu_contra)
    }

    fun irAResetPassword(view: View) {
        val intent = Intent(this, ResetPassword::class.java)
        startActivity(intent)
    }
}