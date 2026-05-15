package com.example.swiftly

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val title = findViewById<TextView>(R.id.splashTitle)
        val tagline = findViewById<TextView>(R.id.splashTagline)

        // make them visible immediately, then animate
        title.alpha = 0f
        title.visibility = View.VISIBLE
        tagline.alpha = 0f
        tagline.visibility = View.VISIBLE

        // fade in title after 500ms
        title.postDelayed({
            title.animate().alpha(1f).setDuration(1500).start()
        }, 500)

        // fade in tagline after 1200ms
        tagline.postDelayed({
            tagline.animate().alpha(1f).setDuration(1500).start()
        }, 1200)

        // go to main screen after 4 seconds
        title.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, 4000)
    }
}