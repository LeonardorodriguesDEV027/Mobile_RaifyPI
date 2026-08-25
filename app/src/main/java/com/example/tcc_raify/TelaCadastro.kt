package com.example.tcc_raify

import android.os.Bundle
import android.provider.ContactsContract
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class TelaCadastro : AppCompatActivity() {

    private lateinit var edtNomeCompletoCadastro: EditText
    private lateinit var edtEmailCadastro: EditText
    private lateinit var edtTelefoneCadastro: EditText
    private lateinit var edtSenhaCadastro: EditText
    private lateinit var edtConfirmarSenhaCadastro: EditText
    private lateinit var btnCadastrar: MaterialButton
    private lateinit var btnVoltarCadastro: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tela_cadastro)

        // Referencias dos elementos da tela
        edtNomeCompletoCadastro = findViewById(R.id.edtNomeCompletoCadastro)
        edtEmailCadastro = findViewById(R.id.edtEmailCadastro)
        edtTelefoneCadastro = findViewById(R.id.edtTelefoneCadastro)
        edtSenhaCadastro = findViewById(R.id.edtSenhaCadastro)
        edtConfirmarSenhaCadastro = findViewById(R.id.edtConfirmarSenhaCadastro)
        btnCadastrar = findViewById(R.id.btnCadastrar)
        btnVoltarCadastro = findViewById(R.id.btnVoltarCadastro)

        // Botão Voltar -> voltar para a tela tela de Login
        btnVoltarCadastro.setOnClickListener {
            finish() // fecha a tela de cadastro e volta para a anterior (Login)
        }
        // Botão Cadastrar
        btnCadastrar.setOnClickListener {
            val nomeCadastro = edtNomeCompletoCadastro.text.toString().trim()
            val emailCadastro = edtEmailCadastro.text.toString().trim()
            val telefoneCadastro = edtTelefoneCadastro.text.toString().trim()
            val senhaCadastro = edtSenhaCadastro.text.toString().trim()
            val condfirmarSenhaCadastro = edtConfirmarSenhaCadastro.toString().trim()

            if (validarCampos(nomeCadastro, emailCadastro, telefoneCadastro, senhaCadastro, condfirmarSenhaCadastro)) {
                //TODO: aqui depois entra a chamada para salvar no banco de dados / API
                Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()

                // Voltar para a tela de login após cadastrar
                finish()
            }
        }
    }
    private fun validarCampos(
        nomeCadastro: String,
        emailCadastro: String,
        telefoneCadastro: String,
        senhaCadastro: String,
        confirmarSenhaCadastro: String
    ): Boolean {
        if (nomeCadastro.isEmpty()) {
            edtNomeCompletoCadastro.error = "Informe seu nome"
            edtNomeCompletoCadastro.requestFocus()
            return false
        }

    }
}