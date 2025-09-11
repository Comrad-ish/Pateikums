package com.packy.pateikums.network

import com.packy.pateikums.model.Event
import retrofit2.http.GET

interface ApiService {
    @GET("wp-json/sem/v1/events")
    suspend fun getEvents(): List<Event>
}