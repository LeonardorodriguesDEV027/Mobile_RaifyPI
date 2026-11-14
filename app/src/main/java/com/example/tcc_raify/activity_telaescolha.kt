package com.example.tcc_raify

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_telaescolha : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_telaescolha)

        // Referencias das views
        val btnVoltar = findViewById<android.widget.LinearLayout>(R.id.btnVoltar)
        val cardAgricultor = findViewById<android.widget.LinearLayout>(R.id.cardAgricultor)
        val cardAgronomo = findViewById<android.widget.LinearLayout>(R.id.cardAgronomo)

        // Botão Voltar: retorna para a tela inicial do app
        btnVoltar.setOnClickListener {
            val intent = Intent(this, activity_telainicial::class.java)

            // Limpa a pilha de telas para não empilhar a tela de escolha de novo
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        // Card Verde: vai para o login Agricultor
        cardAgricultor.setOnClickListener {
            val intent = Intent(this, activity_loginagricultor::class.java)
            startActivity(intent)
        }

        // Card Roxo: vai para o login do Agronômo
        cardAgronomo.setOnClickListener {
            val intent = Intent(this, activity_loginagronomo::class.java)
            startActivity(intent)
        }
    }

    // Faz o botão físico de voltar do celular se comportar igual ao btnVoltar
    override fun onBackPressed() {
        val intent = Intent(this, activity_telainicial::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
        finish()
    }
}