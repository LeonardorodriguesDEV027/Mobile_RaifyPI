package com.example.tcc_raify
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RedefinirSenhaActivity : AppCompatActivity() {

    private lateinit var etNovaSenha: EditText
    private lateinit var etConfirmaSenha: EditText
    private lateinit var btnRedefinir: Button

    companion object {
        private const val TAMANHO_MINIMO_SENHA = 6
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_redefinir_senha)

        etNovaSenha = findViewById(R.id.etNovaSenha)
        etConfirmaSenha = findViewById(R.id.etConfirmaSenha)
        btnRedefinir = findViewById(R.id.btnRedefinir)

        val tvVoltar = findViewById<TextView>(R.id.tvVoltar)
        tvVoltar.setOnClickListener {
            finish() // volta pra tela anterior (envio do código)
        }

        btnRedefinir.setOnClickListener {
            validarERedefinirSenha()
        }
    }

    private fun validarERedefinirSenha() {
        val novaSenha = etNovaSenha.text.toString().trim()
        val confirmaSenha = etConfirmaSenha.text.toString().trim()

        when {
            novaSenha.isEmpty() || confirmaSenha.isEmpty() -> {
                Toast.makeText(this, "Preencha os dois campos de senha", Toast.LENGTH_SHORT).show()
            }
            novaSenha.length < TAMANHO_MINIMO_SENHA -> {
                Toast.makeText(
                    this,
                    "A senha deve ter no mínimo $TAMANHO_MINIMO_SENHA caracteres",
                    Toast.LENGTH_SHORT
                ).show()
            }
            novaSenha != confirmaSenha -> {
                Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show()
            }
            else -> {
                redefinirSenha(novaSenha)
            }
        }
    }

    private fun redefinirSenha(novaSenha: String) {
        // TODO: chamar aqui sua API/backend para efetivamente trocar a senha
        // Exemplo:
        // apiService.redefinirSenha(token, novaSenha) { sucesso ->
        //     if (sucesso) { ... } else { ... }
        // }

        Toast.makeText(this, "Senha redefinida com sucesso!", Toast.LENGTH_SHORT).show()
        finish()
        // ou navegue para a tela de login:
        // startActivity(Intent(this, LoginActivity::class.java))
    }
}