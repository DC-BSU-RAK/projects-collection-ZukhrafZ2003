package com.example.afterthoughts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class QuotesFragment : Fragment() {

    private val quotes = listOf(
        "The present moment is the only moment available to us, and it is the door to all moments." to "Thich Nhat Hanh",
        "You don't have to be positive all the time. It's perfectly okay to feel sad, angry, annoyed, frustrated, scared and anxious." to "Lori Deschene",
        "Almost everything will work again if you unplug it for a few minutes - including you." to "Anne Lamott",
        "In the middle of difficulty lies opportunity." to "Albert Einstein",
        "You are allowed to be both a masterpiece and a work in progress simultaneously." to "Sophia Bush",
        "Self-care is how you take your power back." to "Lalah Delia",
        "Rest when you're weary. Refresh and renew yourself, your body, your mind, your spirit." to "Ralph Marston",
        "The most important relationship in your life is the relationship you have with yourself." to "Diane Von Furstenberg",
        "Owning our story and loving ourselves through that process is the bravest thing we will ever do." to "Brené Brown",
        "You are enough just as you are." to "Meghan Markle",
        "Be gentle with yourself. You are a child of the universe." to "Max Ehrmann",
        "What you are is enough. What you have is enough. Who you are is enough." to "Unknown",
        "Not all those who wander are lost." to "J.R.R. Tolkien",
        "In the end, just three things matter: how well we have lived, how well we have loved, how well we have learned to let go." to "Jack Kornfield",
        "You can't go back and change the beginning, but you can start where you are and change the ending." to "C.S. Lewis",
        "The only way out is through." to "Robert Frost",
        "Breathing in, I calm body and mind. Breathing out, I smile." to "Thich Nhat Hanh",
        "Think of your energy as if it's expensive. As if it's an luxury item. Not everyone can afford it." to "Taylor Swift",
        "May your heart remain breakable but never by the same hand twice." to "Taylor Swift",
        "Give yourself the same compassion you would give a good friend." to "Unknown"
    )

    private var currentIndex = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_quotes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvQuote = view.findViewById<TextView>(R.id.tvQuote)
        val tvAuthor = view.findViewById<TextView>(R.id.tvQuoteAuthor)
        val btnNew = view.findViewById<Button>(R.id.btnNewQuote)

        currentIndex = (System.currentTimeMillis() / 86400000).toInt() % quotes.size
        showQuote(tvQuote, tvAuthor)

        btnNew.setOnClickListener {
            currentIndex = (currentIndex + 1) % quotes.size
            tvQuote.animate().alpha(0f).setDuration(300).withEndAction {
                showQuote(tvQuote, tvAuthor)
                tvQuote.animate().alpha(1f).setDuration(300).start()
            }.start()
        }
    }

    private fun showQuote(tvQuote: TextView, tvAuthor: TextView) {
        val (quote, author) = quotes[currentIndex]
        tvQuote.text = "\"$quote\""
        tvAuthor.text = "- $author"
    }
}