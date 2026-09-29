package com.example.tcc_raify

import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_telaescolha : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_telaescolha)

        val cardAgronomo = findViewById<LinearLayout>(R.id.cardAgronomo)
        val paddingBaseAgronomo = cardAgronomo.paddingBottom

        // Topo respeita a barra de status; o card roxo vai até o fim da tela
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            cardAgronomo.setPadding(
                cardAgronomo.paddingLeft,
                cardAgronomo.paddingTop,
                cardAgronomo.paddingRight,
                paddingBaseAgronomo + systemBars.bottom
            )
            insets
        }

        findViewById<ImageButton>(R.id.btnVoltar).setOnClickListener {
            finish()
        }

        findViewById<LinearLayout>(R.id.cardAgricultor).setOnClickListener {
            // TODO: trocar pela tela de destino do agricultor
            // startActivity(Intent(this, activity_login::class.java).putExtra("perfil", "agricultor"))
        }

        cardAgronomo.setOnClickListener {
            // TODO: trocar pela tela de destino do agrônomo
            // startActivity(Intent(this, activity_login::class.java).putExtra("perfil", "agronomo"))
        }
    }
}