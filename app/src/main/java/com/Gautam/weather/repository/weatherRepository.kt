package com.Gautam.weather.repository

import android.util.Log
import com.Gautam.weather.data.DataOrExceptions
import com.Gautam.weather.model.responces.WeatherResponse
import com.Gautam.weather.network.WeatherAPIs
import javax.inject.Inject

class weatherRepository @Inject constructor(val apIs: WeatherAPIs) {

    private var dataOrExceptions = DataOrExceptions<
            WeatherResponse,
            Boolean,
            Exception>()
    suspend fun GetWeatherApi(city : String) : DataOrExceptions<
            WeatherResponse,
            Boolean,
            Exception>
    {
        dataOrExceptions.isLoading = true
       try {
           val response = apIs.GetWeather(city)
           dataOrExceptions.data = response
           dataOrExceptions.isLoading = false
       }catch (e : Exception)
       {
           Log.e("Repository","exception we found at GetWeatherApis is ${e.message}")
           dataOrExceptions.exceptions = e
           dataOrExceptions.isLoading = false
       }
       return dataOrExceptions
    }
}