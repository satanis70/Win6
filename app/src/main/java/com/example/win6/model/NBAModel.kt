package com.example.win6.model


import com.google.gson.annotations.SerializedName

data class NBAModel(
    val errors: List<Any>,
    val `get`: String,
    val parameters: Parameters,
    val response: List<Response>,
    val results: Int
)