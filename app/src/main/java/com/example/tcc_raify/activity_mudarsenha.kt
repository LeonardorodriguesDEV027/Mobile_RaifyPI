package com.example.tcc_raify

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.firebase.functions.FirebaseFunctions


class activity_mudarsenha : AppCompatActivity() {
    private lateinit var edtNovaSenha: EditText
    private lateinit var edtConfirmarSenha: EditText
    private lateinit var txtErroSenha: TextView
    private lateinit var btnRedefinir: MaterialButton
    private lateinit var btnVoltar: View

    private val functions by lazy { FirebaseFunctions.getInstance() }

    // Vieram da tela de verificação de código
    private val email: String by lazy { intent.getStringExtra("EXTRA_EMAIL") ?: ""}
    private val codigo: String by lazy { intent.getStringExtra("EXTRA_CODIGO") ?: ""}

    private val TAMANHO_MINIMO_SENHA = 8

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mudarsenha)

        if (email.isEmpty() || codigo.isEmpty()) {
            Toast.makeText(this, "Sessão inválida. Renicie o processo.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        edtNovaSenha = findViewById(R.id.edtNSR)    // Nova Senha
        edtConfirmarSenha = findViewById(R.id.edtCSR)   // Confirma Senha
        btnRedefinir = findViewById(R.id.btnRedefinir)
        btnVoltar = findViewById(R.id.btnVoltar)

        btnVoltar.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        btnRedefinir.setOnClickListener { tentarRedefinirSenha() }
    }

    // Validação local antes de chamar o backend
    private fun tentarRedefinirSenha() {

    }

}