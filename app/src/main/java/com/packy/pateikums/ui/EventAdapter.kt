package com.packy.pateikums.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.packy.pateikums.databinding.ItemEventBinding
import com.packy.pateikums.model.Event
import android.text.Html

class EventAdapter(private var events: List<Event>) :
    RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    inner class EventViewHolder(val binding: ItemEventBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.binding.title.text = event.title
        holder.binding.date.text = event.date ?: ""
        holder.binding.thumbnail.load(event.thumbnail)
        holder.binding.description.text = android.text.Html.fromHtml(event.content, Html.FROM_HTML_MODE_COMPACT)
    }

    override fun getItemCount() = events.size

    fun updateData(newEvents: List<Event>) {
        events = newEvents
        notifyDataSetChanged()
    }
}