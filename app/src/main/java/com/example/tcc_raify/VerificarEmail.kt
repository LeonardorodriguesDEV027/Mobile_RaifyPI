package com.example.tcc_raify

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class VerificarEmail : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var btnEnviarCodigo: MaterialButton
    private lateinit var btnVoltar: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_verificar_email)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Referências dos elementos da tela
        edtEmail = findViewById(R.id.edtEmail)
        btnEnviarCodigo = findViewById(R.id.btnEnviarCodigo)
        btnVoltar = findViewById(R.id.btnVoltar)

        // Clique no botão "Enviar o código"
        btnEnviarCodigo.setOnClickListener {
            val email = edtEmail.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(this, "Digite seu e-mail", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Digite um e-mail válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, InserirCodigoActivity::class.java)
            intent.putExtra("EMAIL_USUARIO", email)
            startActivity(intent)
        }

        // Clique no botão "Voltar para o Login"
        btnVoltar.setOnClickListener {
            finish()
        }
    }
}