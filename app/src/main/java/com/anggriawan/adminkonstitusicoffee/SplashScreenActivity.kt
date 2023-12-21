package com.anggriawan.adminkonstitusicoffee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Inisialisasi ProgressBar
        progressBar = findViewById(R.id.progressBar)

        // Mengatur Handler untuk menangani penundaan
        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Menutup SplashScreen setelah LoginActivity dimulai
        }, 3000) // Menunda selama 3000 milidetik (3 detik)
    }
}