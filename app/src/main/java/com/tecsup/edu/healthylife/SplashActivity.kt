package com.tecsup.edu.healthylife

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.TypefaceSpan
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var tvTexto: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        tvTexto = findViewById(R.id.tvTexto)


        val textoCompleto = "HEALTHY LIFE"
        val delayMilisegundos = 100L

        escribirTexto(textoCompleto, delayMilisegundos)

        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        }, 4000)

    }


    private fun escribirTexto(texto: String, delay: Long) {
        val spannableStringBuilder = SpannableStringBuilder()
        var currentIndex = 0

        val handler = Handler()
        val runnable = object : Runnable {
            override fun run() {
                if (currentIndex < texto.length) {
                    val char = texto[currentIndex]
                    spannableStringBuilder.append(char)

                    val spannableString = SpannableString(spannableStringBuilder)
                    spannableString.setSpan(
                        TypefaceSpan("sans-serif-black"),
                        0,
                        spannableStringBuilder.length,
                        0
                    )

                    tvTexto.text = spannableString

                    currentIndex++
                    handler.postDelayed(this, delay)
                }
            }
        }

        handler.postDelayed(runnable, delay)
    }


}