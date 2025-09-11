package com.packy.pateikums

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.packy.pateikums.ui.EventAdapter
import com.packy.pateikums.viewmodel.EventViewModel

class HomeActivity : AppCompatActivity() {

    private val viewModel: EventViewModel by viewModels()
    private lateinit var adapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_layout)

        val recyclerView: RecyclerView = findViewById(R.id.eventsRecyclerView)
        adapter = EventAdapter(emptyList())
        recyclerView.adapter = adapter

        viewModel.events.observe(this) { events ->
            adapter.updateData(events)
        }

        viewModel.error.observe(this) { err ->
            Toast.makeText(this, err, Toast.LENGTH_SHORT).show()
        }

        viewModel.fetchEvents()
    }
}