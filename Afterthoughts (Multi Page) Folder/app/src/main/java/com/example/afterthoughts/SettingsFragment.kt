package com.example.afterthoughts

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Switch
import androidx.fragment.app.Fragment

class SettingsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireContext().getSharedPreferences("journal_prefs", Context.MODE_PRIVATE)

        val switchPoetic = view.findViewById<Switch>(R.id.switchPoetic)
        val switchDates = view.findViewById<Switch>(R.id.switchDates)
        val btnAbout = view.findViewById<LinearLayout>(R.id.btnAbout)

        switchPoetic.isChecked = prefs.getBoolean("poetic_mode", true)
        switchDates.isChecked = prefs.getBoolean("show_dates", true)

        switchPoetic.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("poetic_mode", isChecked).apply()
        }

        switchDates.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("show_dates", isChecked).apply()
        }

        btnAbout.setOnClickListener {
            AboutModalFragment().show(parentFragmentManager, "about")
        }
    }
}