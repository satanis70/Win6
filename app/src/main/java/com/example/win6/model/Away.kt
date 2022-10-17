package com.example.win6.model


import com.google.gson.annotations.SerializedName

data class Away(
    @SerializedName("over_time")
    val overTime: Any,
    @SerializedName("quarter_1")
    val quarter1: Int,
    @SerializedName("quarter_2")
    val quarter2: Int,
    @SerializedName("quarter_3")
    val quarter3: Int,
    @SerializedName("quarter_4")
    val quarter4: Int,
    val total: Int
)