package com.example.tcc_raify

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import java.util.Date
import kotlin.random.Random
import android.widget.EditText
import android.widget.Toast

class activity_codigosenha : AppCompatActivity() {

    private lateinit var digitos: List<EditText>
    private lateinit var txtReenviar: TextView
    private lateinit var btnCofirmar: MaterialButton
    private lateinit var btnVoltar: View

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val db by lazy { FirebaseFirestore.getInstance() }
    private val functions by lazy { FirebaseFunctions.getInstance() }

    // E-mail já vem "pré-conectado" com o Firebase: pega direto do usuário logado
    private val usuarioAtual get() = auth.currentUser
    private val emailUsuario: String get() = usuarioAtual?.email ?: ""
    private val uidUsuario: String get() = usuarioAtual?.uid ?: ""

    // Validade do código em minutos
    private val VALIDADE_CODIGO_MINUTOS = 10

    // Nome da coleção no Firestore (usado em todo o arquivo, sempre igual)
    private val COLECAO_CODIGOS = "codigosVerificacao"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_codigosenha)

        if (usuarioAtual == null) {
            // Não tem usuário logado no Firebase Auth -> não tem como seguir o fluxo
            Toast.makeText(this, "Sessão expirada. Faça login novamente.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        digitos = listOf(
            findViewById(R.id.digito1),
            findViewById(R.id.digito2),
            findViewById(R.id.digito3),
            findViewById(R.id.digito4),
            findViewById(R.id.digito5),
            findViewById(R.id.digito6)
        )

        txtReenviar = findViewById(R.id.txtReenviar)
        btnCofirmar = findViewById(R.id.btnConfirmar)
        btnVoltar = findViewById(R.id.btnVoltar)

        configurarCamposDeCodigo()
        configurarBotaoVoltar()
        configurarBotaoConfirmar()

        // Já dispara o primeiro código automaticamente ao abrir a tela
        txtReenviar.setOnClickListener { reenviarCodigo() }
        reenviarCodigo()
    }

    // Botão Voltar
    private fun configurarBotaoVoltar() {
        btnVoltar.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    // Campos só aceitam números + auto-avanço entre as 6 caixinhas
    private fun configurarCamposDeCodigo() {
        digitos.forEach { it.inputType = InputType.TYPE_CLASS_NUMBER }

        digitos.forEachIndexed { index, campoAtual ->
            campoAtual.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    if (!s.isNullOrEmpty()) {
                        if (index < digitos.size - 1) {
                            digitos[index + 1].requestFocus()
                        } else {
                            campoAtual.clearFocus()
                            esconderTeclado(campoAtual)
                        }
                    }
                }
            })

            campoAtual.setOnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_DEL &&
                    event.action == KeyEvent.ACTION_DOWN &&
                    campoAtual.text.isNullOrEmpty() &&
                    index > 0
                ) {
                    digitos[index - 1].apply {
                        requestFocus()
                        text?.clear()
                    }
                    true
                } else {
                    false
                }
            }
        }
    }

    private fun obterCodigoDigitado(): String =
        digitos.joinToString(separator = "") { it.text.toString() }

    private fun limparCampos() {
        digitos.forEach { it.text?.clear() }
        digitos.first().requestFocus()
    }

    private fun esconderTeclado(view: View) {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    // Reenviar código -> gera código, salva no Firestore, dispara e-mail
    private fun reenviarCodigo() {
        txtReenviar.isEnabled = false

        val codigoGerado = Random.nextInt(100000, 999999).toString()

        // Salva o código gerado no Firestore, vinculado ao uid do usuário
        val dadosCodigo = hashMapOf(
            "codigo" to codigoGerado,
            "email" to emailUsuario,
            "criadoEm" to Date(),
            "usado" to false
        )

        // Conexão com o banco de dados
        db.collection(COLECAO_CODIGOS)
            .document(uidUsuario)
            .set(dadosCodigo)
            .addOnSuccessListener {
                dispararEnvioDeEmail(codigoGerado)
            }
            .addOnFailureListener {
                txtReenviar.isEnabled = true
                Toast.makeText(this, "Erro ao gerar código. Tente novamente.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun dispararEnvioDeEmail(codigo: String) {
        // O envio de e-mail em si NÃO pode ser feito direto do app.
        // O caminho correto é chamar uma Cloud Function (Firebase Functions),
        // que recebe o e-mail + código e usa um serviço de e-mail (ex: SendGrid,
        // Nodemailer) para efetivamente disparar a mensagem.
        //
        // Exemplo de Cloud Function (Node.js) que você precisaria criar:
        //
        // exports.enviarCodigoVerificacao = functions.https.onCall(async (data) => {
        //     const { email, codigo } = data;
        //     // ... lógica de envio via SendGrid/Nodemailer aqui
        // });

        val dados = hashMapOf(
            "email" to emailUsuario,
            "codigo" to codigo
        )

        functions.getHttpsCallable("enviarCodigoVerificacao")
            .call(dados)
            .addOnSuccessListener {
                Toast.makeText(this, "Código enviado para $emailUsuario", Toast.LENGTH_SHORT).show()
                limparCampos()
                iniciarCooldownReenvio()
            }
            .addOnFailureListener {
                txtReenviar.isEnabled = true
                Toast.makeText(this, "Erro ao enviar e-mail. Tente novamente.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun iniciarCooldownReenvio() {
        val duracaoMs = 20_000L
        val textoOriginal = "Reenviar o código"

        object : CountDownTimer(duracaoMs, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                txtReenviar.text = "Reenviar em ${millisUntilFinished / 1000}s"
            }

            override fun onFinish() {
                txtReenviar.text = textoOriginal
                txtReenviar.isEnabled = true
            }
        }.start()
    }

    // Confirmar código -> valida contra o Firestore e navega
    private fun configurarBotaoConfirmar() {
        btnCofirmar.setOnClickListener {
            val codigoDigitado = obterCodigoDigitado()

            if (codigoDigitado.length < 6) {
                Toast.makeText(this, "Preencha todos os 6 dígitos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            btnCofirmar.isEnabled = false
            validarCodigoNoFirestore(codigoDigitado)
        }
    }

    private fun validarCodigoNoFirestore(codigoDigitado: String) {
        db.collection(COLECAO_CODIGOS)
            .document(uidUsuario)
            .get()
            .addOnSuccessListener { documento ->
                btnCofirmar.isEnabled = true

                if (!documento.exists()) {
                    Toast.makeText(this, "Solicite um novo código", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }

                val codigoSalvo = documento.getString("codigo")
                val criadoEm = documento.getDate("criadoEm")
                val jaUsado = documento.getBoolean("usado") ?: false

                val expirado = criadoEm?.let {
                    val diferencaMinutos = (Date().time - it.time) / 60000
                    diferencaMinutos > VALIDADE_CODIGO_MINUTOS
                } ?: true

                when {
                    jaUsado -> Toast.makeText(this, "Código já utilizado. Solicite um novo.", Toast.LENGTH_SHORT).show()
                    expirado -> Toast.makeText(this, "Código expirado. Solicite um novo.", Toast.LENGTH_SHORT).show()
                    codigoDigitado != codigoSalvo -> Toast.makeText(this, "Código inválido", Toast.LENGTH_SHORT).show()

                    else -> {
                        // Marca como usado e segue para redefinir a senha
                        db.collection(COLECAO_CODIGOS).document(uidUsuario)
                            .update("usado", true)
                        irParaRedefinirSenha(codigoDigitado)
                    }
                }
            }
            .addOnFailureListener {
                btnCofirmar.isEnabled = true
                Toast.makeText(this, "Erro ao validar código. Tente novamente.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun irParaRedefinirSenha(codigo: String) {
        val intent = Intent(this, activity_mudarsenha::class.java).apply {
            putExtra("EXTRA_EMAIL", emailUsuario)
            putExtra("EXTRA_CODIGO", codigo)
        }
        startActivity(intent)
        finish()
    }
}