package com.Gautam.weather.screens.settingScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.Gautam.weather.widigits.WeatherAppBar


@Composable
fun WeatherSettingScreen(navController: NavController) {
    SettingScreen(navController)
}

@Composable
fun SettingScreen(navController: NavController) {
    Surface(modifier = Modifier.fillMaxSize(),
        color = Color.White) {
        WeatherAppBar(navController = navController,
            title = "Setting",
            isMainScreen = false,
            icon = Icons.Rounded.ArrowBack,
            elevation = 0.dp){
            navController.popBackStack()
        }
    }
}