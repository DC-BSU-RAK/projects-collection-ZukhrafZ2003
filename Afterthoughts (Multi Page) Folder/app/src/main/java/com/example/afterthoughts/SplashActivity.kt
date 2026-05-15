package com.example.afterthoughts

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val title = findViewById<TextView>(R.id.splashTitle)
        val tagline = findViewById<TextView>(R.id.splashTagline)

        title.alpha = 0f
        tagline.alpha = 0f

        title.postDelayed({
            title.animate().alpha(1f).setDuration(1800).start()
        }, 800)

        tagline.postDelayed({
            tagline.animate().alpha(1f).setDuration(1800).start()
        }, 1600)

        title.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, 4000)
    }
}