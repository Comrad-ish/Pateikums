package com.packy.pateikums

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.packy.pateikums.ui.EventAdapter
import com.packy.pateikums.viewmodel.EventViewModel
import androidx.appcompat.app.AppCompatDelegate

class HomeActivity : AppCompatActivity() {

    private val viewModel: EventViewModel by viewModels()
    private lateinit var adapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Force Light Mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.home_layout)

        setupToolbar()
        setupRecyclerView()
        observeViewModel()

        viewModel.fetchEvents()
    }

    private fun setupToolbar() {
        val toolbar: MaterialToolbar = findViewById(R.id.topAppBar)
        setSupportActionBar(toolbar)

        toolbar.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        /*
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
        */

    }

    private fun setupRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.eventsRecyclerView)
        adapter = EventAdapter(emptyList())
        recyclerView.adapter = adapter
    }

    private fun setupTagSpinner(events: List<com.packy.pateikums.model.Event>) {
        val spinner: Spinner = findViewById(R.id.tagSpinner)

        val tags = mutableSetOf<String>()
        events.forEach { tags.addAll(it.tags) }

        val tagList = listOf("--Visi notikumi--") + tags.sorted()

        val spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            tagList
        ).also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item) // for dropdown items
        spinner.adapter = spinnerAdapter

        // Set default to --Svarīgākie-- if it exists
        val defaultIndex = tagList.indexOf("--Svarīgākie--")
        if (defaultIndex >= 0) {
            spinner.setSelection(defaultIndex)
            adapter.filterByTag("--Svarīgākie--")
        }else {
            // fallback to --VISI-- if --Svarīgākie-- not in tags
            spinner.setSelection(0)
            adapter.filterByTag("--VISI--")
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedTag = tagList[position]
                adapter.filterByTag(selectedTag)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun observeViewModel() {
        viewModel.events.observe(this) { events ->
            adapter.updateData(events)
            setupTagSpinner(events)
        }

        viewModel.error.observe(this) { err ->
            Toast.makeText(this, err, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        menuInflater.inflate(R.menu.top_app_bar_menu, menu)
        return true
    }
}
