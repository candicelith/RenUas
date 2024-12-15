package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

data class Supplies(
    @SerializedName("jenis")
    val jenis: String,

    @SerializedName("warna")
    val warna: String,

    @SerializedName("harga")
    val harga: String
)
