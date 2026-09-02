package com.example.tcc_raify

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class RecoverCodeActivity : AppCompatActivity() {

    private lateinit var btnVoltar: android.widget.Button
    private lateinit var btnConfirmar: MaterialButton

    private lateinit var et1: EditText
    private lateinit var et2: EditText
    private lateinit var et3: EditText
    private lateinit var et4: EditText
    private lateinit var et5: EditText
    private lateinit var et6: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_recover_code_activity)

        btnVoltar = findViewById(R.id.btnVoltar)
        btnConfirmar = findViewById(R.id.btnConfirmar)

        et1 = findViewById(R.id.et1)
        et2 = findViewById(R.id.et2)
        et3 = findViewById(R.id.et3)
        et4 = findViewById(R.id.et4)
        et5 = findViewById(R.id.et5)
        et6 = findViewById(R.id.et6)

        val codeBoxes = arrayOf(et1, et2, et3, et4, et5, et6)

        // Auto-foco e preenchimento
        for (i in codeBoxes.indices) {
            codeBoxes[i].addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val value = s?.toString()?.trim().orEmpty()
                    if (value.length == 1) {
                        if (i < codeBoxes.lastIndex) codeBoxes[i + 1].requestFocus()
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }

        // Voltar
        btnVoltar.setOnClickListener { finish() }

        // Confirmar
        btnConfirmar.setOnClickListener {
            val codigo = codeBoxes.joinToString("") { it.text.toString().trim() }

            if (codigo.length < 6) {
                // Você pode trocar por Toast / Snackbar
                android.widget.Toast.makeText(this, "Digite os 6 dígitos do código", android.widget.Toast.LENGTH_SHORT).show()
            } else {
                // TODO: validar código e navegar
                android.widget.Toast.makeText(this, "Código: $codigo", android.widget.Toast.LENGTH_SHORT).show()
            }
        }

        // Reenviar (placeholder)
        findViewById<android.widget.TextView>(R.id.txtReenviar).setOnClickListener {
            // TODO: chamar API para reenviar
            android.widget.Toast.makeText(this, "Enviando código novamente...", android.widget.Toast.LENGTH_SHORT).show()
        }

        // Inicializar foco
        et1.requestFocus()
    }
}