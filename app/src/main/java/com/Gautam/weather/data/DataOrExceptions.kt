package com.Gautam.weather.data

data class DataOrExceptions<T,Boolean,e: Exception>(
    var data: T? = null,
    var isLoading : Boolean? = null,
    var exceptions: e? = null)