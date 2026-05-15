package com.example.afterthoughts

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TodayFragment : Fragment() {

    private val selectedMoods = mutableListOf<String>()
    private lateinit var moodGrid: GridLayout
    private lateinit var tvReflection: TextView

    private val moods = listOf(
        "☀️" to "Joy", "🌧️" to "Sad", "🔥" to "Passion", "🌿" to "Calm",
        "💭" to "Pensive", "⚡" to "Anxious", "🌊" to "Overwhelmed", "🍂" to "Nostalgic",
        "✨" to "Grateful", "🌙" to "Tired", "💪" to "Strong", "🫶" to "Loved"
    )

    private val poeticResponses = mapOf(
        setOf("☀️") to "Something golden lives in you today. Let it shine without apology.",
        setOf("🌧️") to "Even the heaviest rain clears eventually. You are allowed to feel this.",
        setOf("🔥") to "That fire in you is not a flaw. It is the thing that moves mountains.",
        setOf("🌿") to "You found stillness today. That is its own kind of courage.",
        setOf("💭") to "The quiet thoughts are often the most honest ones. Listen gently.",
        setOf("⚡") to "Anxiety is just excitement without direction. You will find your way.",
        setOf("🌊") to "When the waves are too many, you do not have to swim. You can float.",
        setOf("🍂") to "Nostalgia is love with nowhere to go. It means something mattered.",
        setOf("✨") to "Gratitude is the quietest form of joy. You already have enough.",
        setOf("🌙") to "Rest is not giving up. Rest is how you begin again.",
        setOf("💪") to "You are stronger than the version of yourself that doubted this.",
        setOf("🫶") to "To be loved is a gift. To know it is wisdom.",
        setOf("☀️", "🌧️") to "You held joy and sadness at once today. That is what it means to be fully alive.",
        setOf("🔥", "🌿") to "Passion and peace are not opposites. You are learning to hold both.",
        setOf("💭", "🌙") to "A tired mind thinks the deepest thoughts. Rest now. The answers will wait.",
        setOf("🌊", "⚡") to "The storm inside you is real. But storms always pass. Always.",
        setOf("✨", "🫶") to "Gratitude and love on the same day. You are exactly where you need to be.",
        setOf("🍂", "🌧️") to "Grief and memory walked beside you today. That means you loved well.",
        setOf("☀️", "💪") to "You were both bright and strong today. That combination is rare. Cherish it.",
        setOf("🌿", "💭") to "Stillness and thought together. You are growing in ways you cannot yet see.",
        setOf("🔥", "⚡") to "Intensity in every direction today. Channel it. You are electric.",
        setOf("🌙", "🍂") to "Tired and nostalgic - a quietly heavy combination. Be gentle with yourself tonight.",
        setOf("🫶", "☀️") to "Loved and joyful. This is what the good days feel like. Remember this one.",
    )

    private val fallbacks = listOf(
        "Every feeling you carried today was valid. Every single one.",
        "You showed up. On the hard days, that is everything.",
        "The fact that you paused to reflect means you are paying attention. That matters.",
        "Whatever today held, you held it. That is enough.",
        "Some days defy description. This might be one of them. That is okay."
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_today, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvDate = view.findViewById<TextView>(R.id.tvDate)
        moodGrid = view.findViewById(R.id.moodGrid)
        tvReflection = view.findViewById(R.id.tvReflection)
        val btnReflect = view.findViewById<Button>(R.id.btnReflect)
        val btnSave = view.findViewById<Button>(R.id.btnSave)
        val etNote = view.findViewById<android.widget.EditText>(R.id.etNote)

        val dateFormat = SimpleDateFormat("EEEE, d MMMM yyyy", Locale.getDefault())
        tvDate.text = dateFormat.format(Date())

        buildMoodGrid()

        btnReflect.setOnClickListener {
            if (selectedMoods.isEmpty()) {
                Toast.makeText(requireContext(), "tap a mood first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val response = poeticResponses[selectedMoods.toSet()] ?: fallbacks.random()
            tvReflection.text = response
            tvReflection.alpha = 0f
            tvReflection.animate().alpha(1f).setDuration(600).start()
        }

        btnSave.setOnClickListener {
            val note = etNote.text.toString()
            val reflection = tvReflection.text.toString()
            if (selectedMoods.isNotEmpty() && reflection != "tap your moods and press reflect...") {
                val prefs = requireContext().getSharedPreferences("journal_prefs", Context.MODE_PRIVATE)
                val dateStr = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())
                val entry = "$dateStr||${selectedMoods.joinToString(",")}||$note||$reflection"
                val existing = prefs.getString("entries", "") ?: ""
                val updated = if (existing.isEmpty()) entry else "$existing\n$entry"
                prefs.edit().putString("entries", updated).apply()

                // clear after save
                tvReflection.text = "tap your moods and press reflect..."
                etNote.setText("")
                selectedMoods.clear()
                moodGrid.removeAllViews()
                buildMoodGrid()
                Toast.makeText(requireContext(), "✦ saved!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "select moods and reflect first", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun buildMoodGrid() {
        val screenWidth = resources.displayMetrics.widthPixels
        val padding = (32 * resources.displayMetrics.density).toInt()
        val btnSize = (screenWidth - padding) / 4

        moods.forEach { (emoji, name) ->
            val btn = Button(requireContext())
            btn.text = "$emoji\n$name"
            btn.textSize = 9f
            btn.setTextColor(Color.parseColor("#8aaa8c"))
            btn.setBackgroundColor(Color.parseColor("#111f13"))
            btn.gravity = Gravity.CENTER
            btn.setPadding(4, 4, 4, 4)

            val params = GridLayout.LayoutParams()
            params.width = btnSize
            params.height = btnSize
            params.setMargins(4, 4, 4, 4)
            btn.layoutParams = params

            btn.setOnClickListener {
                if (emoji in selectedMoods) {
                    selectedMoods.remove(emoji)
                    btn.setBackgroundColor(Color.parseColor("#111f13"))
                    btn.setTextColor(Color.parseColor("#8aaa8c"))
                } else if (selectedMoods.size < 3) {
                    selectedMoods.add(emoji)
                    btn.setBackgroundColor(Color.parseColor("#1a2e1c"))
                    btn.setTextColor(Color.parseColor("#e8d9b0"))
                }
            }
            moodGrid.addView(btn)
        }
    }
}