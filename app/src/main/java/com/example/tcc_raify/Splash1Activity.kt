package com.example.tcc_raify

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class Splash1Activity : AppCompatActivity() {

    private val SPLASH_DELAY = 2000L // 2 segudos

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_splash1)

            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, activity_loginagricultor::class.java)
                startActivity(intent)
                finish()
            }, SPLASH_DELAY)
        }
    }