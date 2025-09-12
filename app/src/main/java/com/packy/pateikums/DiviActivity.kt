package com.packy.pateikums

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.packy.pateikums.ui.EventAdapter
import com.packy.pateikums.viewmodel.EventViewModel

//toolbar imports
import com.google.android.material.appbar.MaterialToolbar
import androidx.appcompat.widget.Toolbar

class DiviActivity : AppCompatActivity() {
    private val viewModel: EventViewModel by viewModels()
    private lateinit var adapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.simple_category_layout)

        // Toolbar
        val toolbar: MaterialToolbar = findViewById(R.id.topAppBar)
        setSupportActionBar(toolbar)

        // Title click -> go Home
        toolbar.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        // Menu clicks
        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.category_viens -> {
                    startActivity(Intent(this, ViensActivity::class.java))
                    true
                }
                R.id.category_divi -> {
                    startActivity(Intent(this, DiviActivity::class.java))
                    true
                }
                else -> false
            }
        }

        //Recycler view setup
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

    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        menuInflater.inflate(R.menu.top_app_bar_menu, menu)
        return true
    }
}