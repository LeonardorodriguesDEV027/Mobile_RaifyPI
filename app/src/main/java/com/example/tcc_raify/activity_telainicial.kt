package com.example.tcc_raify

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class activity_telainicial : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_telainicial)

        // Botão "Já tenho conta" -> tela de escolha
        findViewById<android.view.View>(R.id.btnLogin).setOnClickListener {
            startActivity(Intent(this, activity_telaescolha::class.java))
        }

        //Botão "Sou novo por aqui" -> tela de cadastro
        findViewById<android.view.View>(R.id.btnCadastro).setOnClickListener {
            startActivity(Intent(this, activity_telacadastro::class.java))
        }
    }
}