package com.Gautam.weather.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Gautam.weather.data.DataOrExceptions
import com.Gautam.weather.model.responces.WeatherResponse
import com.Gautam.weather.repository.weatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class mainViewModel @Inject constructor(private val repository: weatherRepository) : ViewModel() {
    suspend fun GetWeatherData(city: String):DataOrExceptions<WeatherResponse, Boolean, Exception>{
        return repository.GetWeatherApi(city)
    }

}