package com.packy.pateikums.repository

import com.packy.pateikums.model.Event
import com.packy.pateikums.network.RetrofitClient

class EventRepository {
    suspend fun getEvents(): List<Event> = RetrofitClient.api.getEvents()
}