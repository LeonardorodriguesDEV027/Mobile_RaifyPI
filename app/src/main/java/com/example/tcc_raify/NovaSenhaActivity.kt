package com.example.tcc_raify

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class NovaSenha : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nova_senha)

        val btnVoltar = findViewById<LinearLayout>(R.id.btnVoltar)
        val edtNovaSenha = findViewById<EditText>(R.id.edtNovaSenha)
        val edtConfirmaSenha = findViewById<EditText>(R.id.edtConfirmaSenha)
        val btnRedefinir = findViewById<MaterialButton>(R.id.btnRedefinir)

        btnVoltar.setOnClickListener {
            finish()
        }

        btnRedefinir.setOnClickListener {
            val novaSenha = edtNovaSenha.text.toString()
            val confirmaSenha = edtConfirmaSenha.text.toString()

            if (novaSenha.isEmpty() || confirmaSenha.isEmpty()) {
                Toast.makeText(this, "Preencha os dois campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (novaSenha.length < 6) {
                Toast.makeText(this, "A senha precisa ter pelo menos 6 caracteres", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (novaSenha != confirmaSenha) {
                Toast.makeText(this, "As senhas não são iguais", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // aqui depois entra a chamada pra API pra salvar a senha nova
            Toast.makeText(this, "Senha redefinida com sucesso!", Toast.LENGTH_SHORT).show()

            // como essa é a ultima tela do fluxo, volta pro login limpando as telas anteriores
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}