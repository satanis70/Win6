package com.example.win6.model


import com.google.gson.annotations.SerializedName

data class League(
    val id: Int,
    val logo: Any,
    val name: String,
    val season: String,
    val type: String
)