package com.example.marvellisimo.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marvellisimo.R
import kotlinx.android.synthetic.main.history_item_fragment.view.*

interface HistoryListActionListener {
    fun itemClicked(item: String)
}

class HistoryViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    fun setItem(item: String, listener: HistoryListActionListener) {
        view.searchValue.text = item
        view.setOnClickListener { listener.itemClicked(item) }
    }
}

class HistoryViewAdapter(private val items: ArrayList<String>, private val listener: HistoryListActionListener) :
    RecyclerView.Adapter<HistoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.history_item_fragment, parent, false)
        return HistoryViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.setItem(items[position], listener)
    }

}