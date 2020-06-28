package ru.korolevss.kvartirka.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.korolevss.kvartirka.api.API

object Repository {

    private const val BASE_URL = "https://api.kvartirka.com/"

    private var retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    private var api: API = retrofit.create(API::class.java)

    suspend fun getPosts(longitude: Double, latitude: Double, offset: Int) =
        api.getPosts(longitude, latitude, offset)


}