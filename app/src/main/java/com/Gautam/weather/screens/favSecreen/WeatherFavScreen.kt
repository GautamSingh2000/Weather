package com.Gautam.weather.screens.favSecreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
fun WeatherFavScreen(navController: NavController) {
    FavScreen(navController)
}

@Composable
fun FavScreen(navController: NavController) {
    Scaffold(topBar = {
        WeatherAppBar(navController = navController,
            title = "Favourite Cities",
            isMainScreen = false,
            icon = Icons.Rounded.ArrowBack,
            elevation = 0.dp){
            navController.popBackStack()
        }
    }) { innerPadding ->
        FavMainScreen(modifier = Modifier.padding(innerPadding))
    }

}

@Composable
fun FavMainScreen(modifier: Modifier) {
    Surface(modifier = Modifier.fillMaxSize(),
        color = Color.White) {
    }
}
