package com.example.afterthoughts

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class JournalFragment : Fragment() {

    private lateinit var adapter: JournalAdapter
    private lateinit var entries: MutableList<String>
    private lateinit var favourites: MutableSet<Int>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_journal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireContext().getSharedPreferences("journal_prefs", Context.MODE_PRIVATE)

        val raw = prefs.getString("entries", "") ?: ""
        entries = if (raw.isEmpty()) mutableListOf()
        else raw.split("\n").reversed().toMutableList()

        val favSet = prefs.getStringSet("favourites", emptySet()) ?: emptySet()
        favourites = favSet.mapNotNull { it.toIntOrNull() }.toMutableSet()

        adapter = JournalAdapter(
            entries,
            favourites,
            onDelete = { position ->
                entries.removeAt(position)
                favourites.remove(position)
                adapter.notifyItemRemoved(position)
                adapter.notifyItemRangeChanged(position, entries.size)
                saveEntries(prefs)
                saveFavourites(prefs)
            },
            onFavourite = { position ->
                if (favourites.contains(position)) {
                    favourites.remove(position)
                } else {
                    favourites.add(position)
                }
                adapter.notifyItemChanged(position)
                saveFavourites(prefs)
            }
        )

        val recycler = view.findViewById<RecyclerView>(R.id.recyclerJournal)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter
    }

    private fun saveEntries(prefs: android.content.SharedPreferences) {
        val reversed = entries.reversed()
        prefs.edit().putString("entries", reversed.joinToString("\n")).apply()
    }

    private fun saveFavourites(prefs: android.content.SharedPreferences) {
        prefs.edit().putStringSet(
            "favourites",
            favourites.map { it.toString() }.toSet()
        ).apply()
    }
}