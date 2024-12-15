package com.example.myapplication.network

import com.example.myapplication.model.Supplies
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("product/")
    fun getAllSupplies(): Call<List<Supplies>>

}