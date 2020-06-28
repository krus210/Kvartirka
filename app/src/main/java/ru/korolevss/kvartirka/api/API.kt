package ru.korolevss.kvartirka.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.korolevss.kvartirka.model.Model

interface API {

    @GET("client/1.4/flats/?device_screen_width=1920&currency_id=643")
    suspend fun getPosts(
        @Query("point_lng") longitude: Double,
        @Query("point_lat") latitude: Double,
        @Query("offset") offset: Int
    ): Response<Model>

}