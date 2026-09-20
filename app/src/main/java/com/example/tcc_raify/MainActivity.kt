package com.example.tcc_raify

import android.content.Context
import android.content.Intent
import android.content.pm.Checksum
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Patterns
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton


class MainActivity : AppCompatActivity() {
    private lateinit var edtEmail: EditText
    private lateinit var edtSenha: EditText
    private lateinit var checkLembrar: CheckBox
    private lateinit var btnEntrar: MaterialButton
    private lateinit var txtEsqueciSenha: TextView
    private lateinit var btnVoltar: LinearLayout

    private val PREFS_NAME = "RaifyPrefs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /// Referências dos elementos da tela
        edtEmail = findViewById(R.id.edtEmail)
        edtSenha = findViewById(R.id.edtSenha)
        checkLembrar = findViewById(R.id.checkLembrar)
        btnEntrar = findViewById(R.id.btnEntrar)
        txtEsqueciSenha = findViewById(R.id.txtEsqueciSenha)
        btnVoltar = findViewById(R.id.btnVoltar)

        carregarDadosSalvos()

        // Botão Voltar
        btnVoltar.setOnClickListener {
            finish()
        }

        // Esqueci minha senha
        txtEsqueciSenha.setOnClickListener {
            val intent = intent(this, EsqueciSenhaActivity::class.java)
            startActivity(intent)
        }

        //Botão Entrar
        btnEntrar.setOnClickListener {
            val email = edtEmail.text.toString().trim()
            val senha = edtSenha.text.toString().trim()

            if (validarCampos(email, senha)) {
                if (checkLembrar.isChecked) {
                    salvarDados(email, senha)
                    } else {
                        limparDadosSalvos()
                    }
                // TODO: aqui entra a chamada real de autenticação (API, Firebase, etc.)
                irParaDashboard()
                }
            }
        }

    private fun validarCampos(email: String, senha: String): Boolean {
        // função para validar os "inputs" na senha e email
        if (email.isEmpty()) {
            edtEmail.error = "Informe o e-mail"
            edtEmail.requestFocus()
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtEmail.error = "E-mail inválido"
            edtEmail.requestFocus()
            return false
        }
        if (senha.isEmpty()) {
            edtSenha.error = "Informe a senha"
            edtSenha.requestFocus()
            return false
        }
        if (senha.length < 8) {
            edtSenha.error = "A senha deve ter no mínimo 8 caracteres"
            edtSenha.requestFocus()
            return false
        }
        return true
    }
    private fun irParaDashboard() {
        val intent = intent(this, DasboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    // -- Lembrar de mim (SharedPreferences) --
    private fun salvarDados(email: String, senha: String) {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }
    private fun limparDadosSalvos() {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }
    private fun carregarDadosSalvos() {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val lembrar = prefs.getBoolean("lembrar", false)
        if (lembrar) {
            edtEmail.setText(prefs.getString("email", ""))
            edtSenha.setText(prefs.getString("senha", ""))
            checkLembrar.isChecked = true
        }
    }
}