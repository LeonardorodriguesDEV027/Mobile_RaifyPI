package com.example.tcc_raify

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Tela_Historico : AppCompatActivity() {

    private lateinit var tabVisaoGeral: TextView
    private lateinit var tabTransacoes: TextView
    private lateinit var tabAnalises: TextView
    private lateinit var btnFiltroMes: TextView
    private lateinit var btnMenu: ImageButton
    private lateinit var btnNotificacao: ImageButton
    private lateinit var btnPerfil: ImageButton
    private lateinit var logoHeader: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tela_historico)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        setupListeners()
    }

    private fun initViews() {
        tabVisaoGeral = findViewById(R.id.tabVisaoGeral)
        tabTransacoes = findViewById(R.id.tabTransacoes)
        tabAnalises = findViewById(R.id.tabAnalises)
        btnFiltroMes = findViewById(R.id.btnFiltroMes)
        btnMenu = findViewById(R.id.btnMenu)
        btnNotificacao = findViewById(R.id.btnnotificacao)
        btnPerfil = findViewById(R.id.btnPerfil)
        logoHeader = findViewById(R.id.logoHeader)
    }

    private fun setupListeners() {
        // Ao clicar em Visão Geral, volta/vai para a tela de Financeiro (Visão Geral)
        tabVisaoGeral.setOnClickListener {
            val intent = Intent(this, activity_financeiro::class.java)
            startActivity(intent)
            finish()
        }

        tabTransacoes.setOnClickListener {
            // Já está na tela de Transações / Histórico
        }

        tabAnalises.setOnClickListener {
            Toast.makeText(this, "Aba Análises", Toast.LENGTH_SHORT).show()
        }

        btnFiltroMes.setOnClickListener {
            Toast.makeText(this, "Selecionar Mês/Filtro", Toast.LENGTH_SHORT).show()
        }

        btnMenu.setOnClickListener {
            Toast.makeText(this, "Menu aberto", Toast.LENGTH_SHORT).show()
        }

        btnNotificacao.setOnClickListener {
            Toast.makeText(this, "Notificações", Toast.LENGTH_SHORT).show()
        }

        btnPerfil.setOnClickListener {
            Toast.makeText(this, "Perfil", Toast.LENGTH_SHORT).show()
        }

        logoHeader.setOnClickListener {
            Toast.makeText(this, "Raify - Início", Toast.LENGTH_SHORT).show()
        }
    }
}