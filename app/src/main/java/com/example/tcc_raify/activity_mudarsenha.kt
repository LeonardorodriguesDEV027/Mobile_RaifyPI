package com.example.tcc_raify

import android.content.Intent
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
        val novaSenha = edtNovaSenha.text.toString()
        val confirmarSenha = edtConfirmarSenha.toString()

        if (novaSenha.length < TAMANHO_MINIMO_SENHA) {
            Toast.makeText(
                this, "A senha precisa ter mínimo $TAMANHO_MINIMO_SENHA caracteres",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        if (novaSenha != confirmarSenha) {
            Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show()
            return
        }
        redefinirSenhaNoServidor(novaSenha)
    }
    // Troca a senha via Cloud Function (Admin SDK), pois o usuário
    // não está logado nesse momento (fluxo de "esqueci a senha").
    // O Firebase Auth não permite updatePassword() sem login recente,
    // então a validação final do código + a troca de senha precisam
    // acontecer no servidor.
    //

    private fun redefinirSenhaNoServidor(novaSenha: String) {
        btnRedefinir.isEnabled = false

        val dados = hashMapOf(
            "email" to email,
            "codigo" to codigo,
            "novaSenha" to novaSenha
        )

        functions.getHttpsCallable("redefinirSenha")
            .call(dados)
            .addOnSuccessListener {
                Toast.makeText(this, "Senha redefinida com sucesso!", Toast.LENGTH_SHORT).show()
                irParaLogin()
            }
            .addOnFailureListener { erro ->
                btnRedefinir.isEnabled = true
                Toast.makeText(this, erro.menssage ?: "Erro ao redefinir  a senha. Tente novamente.",
                    Toast.LENGTH_SHORT).show()
            }
    }
    private fun irParaLogin() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        finish()
    }

}