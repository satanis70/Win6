package com.example.win6.model


import com.google.gson.annotations.SerializedName

data class Parameters(
    val date: String,
    val league: String,
    val season: String,
    val team: String,
    val timezone: String
)