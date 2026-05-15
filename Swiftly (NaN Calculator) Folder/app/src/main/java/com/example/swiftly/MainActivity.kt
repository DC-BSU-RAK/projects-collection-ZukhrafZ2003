package com.example.swiftly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import android.view.Gravity
import android.graphics.Color
import android.view.View
import android.view.ViewGroup

class MainActivity : AppCompatActivity() {

    private val sequence = mutableListOf<String>()
    private lateinit var tvEquation: TextView
    private lateinit var tvResult: TextView

    private val eras = listOf(
        "debut" to ("🤠" to "Debut"),
        "fearless" to ("✨" to "Fearless"),
        "speak_now" to ("🎭" to "Speak Now"),
        "red" to ("🍂" to "Red"),
        "nineteen89" to ("📷" to "1989"),
        "reputation" to ("🐍" to "Reputation"),
        "lover" to ("🦋" to "Lover"),
        "folklore" to ("🌫️" to "Folklore"),
        "evermore" to ("🍁" to "Evermore"),
        "midnights" to ("🌙" to "Midnights"),
        "ttpd" to ("🪶" to "TTPD"),
        "vault" to ("🗝️" to "Vault")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvEquation = findViewById(R.id.tvEquation)
        tvResult = findViewById(R.id.tvResult)

        buildEraGrid()

        findViewById<Button>(R.id.btnInfo).setOnClickListener {
            InfoModalFragment().show(supportFragmentManager, "info")
        }

        findViewById<Button>(R.id.btnBack).setOnClickListener {
            if (sequence.isNotEmpty()) {
                sequence.removeAt(sequence.lastIndex)
                updateDisplay()
            }
        }

        findViewById<Button>(R.id.btnClear).setOnClickListener {
            sequence.clear()
            updateDisplay()
            tvResult.text = ""
        }

        findViewById<Button>(R.id.btnCalculate).setOnClickListener {
            if (sequence.isNotEmpty()) {
                val result = EraCalculatorEngine.calculate(sequence)
                tvResult.text = result
                tvResult.alpha = 0f
                tvResult.animate().alpha(1f).setDuration(500).start()
            }
        }
    }

    private fun buildEraGrid() {
        val grid = findViewById<GridLayout>(R.id.eraGrid)
        val screenWidth = resources.displayMetrics.widthPixels
        val padding = (32 * resources.displayMetrics.density).toInt()
        val btnSize = (screenWidth - padding) / 4

        eras.forEach { (key, data) ->
            val (emoji, name) = data
            val btn = Button(this)
            btn.text = "$emoji\n$name"
            btn.textSize = 9f
            btn.setTextColor(Color.parseColor("#a78bda"))
            btn.setBackgroundColor(Color.parseColor("#12122a"))
            btn.gravity = Gravity.CENTER
            btn.setPadding(4, 4, 4, 4)

            val params = GridLayout.LayoutParams()
            params.width = btnSize
            params.height = btnSize
            params.setMargins(4, 4, 4, 4)
            btn.layoutParams = params

            btn.setOnClickListener {
                if (sequence.size < 4) {
                    sequence.add(key)
                    updateDisplay()
                    tvResult.text = ""
                    showSparkle(it)
                }
            }
            grid.addView(btn)
        }
    }

    private fun showSparkle(view: View) {
        val sparkles = listOf("✨", "⭐", "💫", "🌟")
        val tv = TextView(this)
        tv.text = sparkles.random()
        tv.textSize = 24f
        tv.x = view.x + (view.width / 2f)
        tv.y = view.y - 20f
        val container = findViewById<ViewGroup>(android.R.id.content)
        container.addView(tv)
        tv.animate()
            .translationYBy(-200f)
            .alpha(0f)
            .setDuration(900)
            .withEndAction { container.removeView(tv) }
            .start()
    }

    private fun updateDisplay() {
        tvEquation.text = if (sequence.isEmpty()) ""
        else sequence.joinToString(" + ") { key ->
            eras.find { it.first == key }?.second?.first ?: "?"
        }
    }
}