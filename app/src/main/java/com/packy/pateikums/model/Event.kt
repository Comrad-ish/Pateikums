package com.packy.pateikums.model

data class Event(
    val id: Int,
    val title: String,
    val content: String,
    val date: String?,
    val time: String?,
    val tags: List<String>,
    val thumbnail: String,
    val permalink: String
)