package com.packy.pateikums.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.packy.pateikums.model.Event
import com.packy.pateikums.repository.EventRepository
import kotlinx.coroutines.launch

class EventViewModel : ViewModel() {
    private val repository = EventRepository()

    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> = _events

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun fetchEvents() {
        viewModelScope.launch {
            try {
                _events.value = repository.getEvents()
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error"
            }
        }
    }
}