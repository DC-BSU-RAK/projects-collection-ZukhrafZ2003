package com.example.afterthoughts

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireContext().getSharedPreferences("journal_prefs", Context.MODE_PRIVATE)

        val etName = view.findViewById<EditText>(R.id.etName)
        val tvTotal = view.findViewById<TextView>(R.id.tvTotalEntries)
        val tvFavs = view.findViewById<TextView>(R.id.tvFavCount)
        val tvTopMood = view.findViewById<TextView>(R.id.tvTopMood)
        val btnSave = view.findViewById<Button>(R.id.btnSaveName)
        val favsContainer = view.findViewById<LinearLayout>(R.id.favsContainer)

        etName.setText(prefs.getString("user_name", ""))

        val raw = prefs.getString("entries", "") ?: ""
        val entries = if (raw.isEmpty()) emptyList() else raw.split("\n")
        tvTotal.text = entries.size.toString()

        val favIndices = prefs.getStringSet("favourites", emptySet()) ?: emptySet()
        tvFavs.text = favIndices.size.toString()

        val moodCount = mutableMapOf<String, Int>()
        entries.forEach { entry ->
            val parts = entry.split("||")
            if (parts.size >= 2) {
                parts[1].split(",").forEach { mood ->
                    moodCount[mood] = (moodCount[mood] ?: 0) + 1
                }
            }
        }
        tvTopMood.text = moodCount.maxByOrNull { it.value }?.key ?: "—"

        btnSave.setOnClickListener {
            prefs.edit().putString("user_name", etName.text.toString()).apply()
            Toast.makeText(requireContext(), "profile saved!", Toast.LENGTH_SHORT).show()
        }

        // show favourite entries
        favsContainer.removeAllViews()
        if (favIndices.isEmpty()) {
            val empty = TextView(requireContext())
            empty.text = "no favourites yet - heart an entry in your journal"
            empty.setTextColor(Color.parseColor("#4a6a4c"))
            empty.textSize = 12f
            empty.typeface = android.graphics.Typeface.create("serif", android.graphics.Typeface.ITALIC)
            empty.setPadding(0, 8, 0, 8)
            favsContainer.addView(empty)
        } else {
            val reversedEntries = entries.reversed()
            favIndices.mapNotNull { it.toIntOrNull() }.sorted().forEach { index ->
                if (index < reversedEntries.size) {
                    val parts = reversedEntries[index].split("||")
                    if (parts.size >= 4) {
                        val card = LinearLayout(requireContext())
                        card.orientation = LinearLayout.VERTICAL
                        card.setBackgroundColor(Color.parseColor("#111f13"))
                        card.setPadding(24, 20, 24, 20)
                        val lp = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        lp.setMargins(0, 0, 0, 16)
                        card.layoutParams = lp

                        val date = TextView(requireContext())
                        date.text = parts[0]
                        date.setTextColor(Color.parseColor("#4a6a4c"))
                        date.textSize = 10f
                        card.addView(date)

                        val moods = TextView(requireContext())
                        moods.text = parts[1].replace(",", "  ")
                        moods.textSize = 18f
                        moods.setPadding(0, 8, 0, 8)
                        card.addView(moods)

                        if (parts[2].isNotEmpty()) {
                            val note = TextView(requireContext())
                            note.text = parts[2]
                            note.setTextColor(Color.parseColor("#8aaa8c"))
                            note.textSize = 12f
                            note.typeface = android.graphics.Typeface.create("serif", android.graphics.Typeface.NORMAL)
                            note.setPadding(0, 0, 0, 6)
                            card.addView(note)
                        }

                        val reflection = TextView(requireContext())
                        reflection.text = parts[3]
                        reflection.setTextColor(Color.parseColor("#e8d9b0"))
                        reflection.textSize = 12f
                        reflection.typeface = android.graphics.Typeface.create("serif", android.graphics.Typeface.ITALIC)
                        card.addView(reflection)

                        favsContainer.addView(card)
                    }
                }
            }
        }
    }
}