package com.example.afterthoughts

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class JournalAdapter(
    private val entries: MutableList<String>,
    private val favourites: MutableSet<Int>,
    private val onDelete: (Int) -> Unit,
    private val onFavourite: (Int) -> Unit
) : RecyclerView.Adapter<JournalAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDate: TextView = view.findViewById(R.id.entryDate)
        val tvMoods: TextView = view.findViewById(R.id.entryMoods)
        val tvNote: TextView = view.findViewById(R.id.entryNote)
        val tvReflection: TextView = view.findViewById(R.id.entryReflection)
        val btnFav: TextView = view.findViewById(R.id.btnFavourite)
        val btnDelete: TextView = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_journal_entry, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val parts = entries[position].split("||")
        if (parts.size >= 4) {
            holder.tvDate.text = parts[0]
            holder.tvMoods.text = parts[1].replace(",", "  ")
            holder.tvNote.text = parts[2]
            holder.tvReflection.text = parts[3]
        }

        val isFav = favourites.contains(position)
        holder.btnFav.text = if (isFav) "♥" else "♡"
        holder.btnFav.setTextColor(
            if (isFav) Color.parseColor("#e8d9b0")
            else Color.parseColor("#4a6a4c")
        )

        holder.btnFav.setOnClickListener {
            onFavourite(holder.adapterPosition)
        }

        holder.btnDelete.setOnClickListener {
            onDelete(holder.adapterPosition)
        }
    }

    override fun getItemCount() = entries.size
}
