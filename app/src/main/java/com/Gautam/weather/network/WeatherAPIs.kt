package com.Gautam.weather.network

import com.Gautam.weather.model.responces.WeatherResponse
import com.Gautam.weather.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherAPIs {
    @GET("data/2.5/forecast/daily")
    suspend fun GetWeather(
        @Query("q")  query :  String ,
        @Query("appid")  appid :  String = Constants.API_KEY ,
        @Query("units")  units :  String = "imperial" ,
    ):WeatherResponse
}