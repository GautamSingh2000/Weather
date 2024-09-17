package com.Gautam.weather.screens.aboutScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.Gautam.weather.utils.Constants
import com.Gautam.weather.widigits.WeatherAppBar

@Composable
fun WeatherAboutScreen(navController: NavController , city : String) {
    AboutScreenAbout(navController,city)
}

@Composable
fun AboutScreenAbout(navController: NavController, city: String) {
   Surface(color = Color.White) {
       Scaffold(topBar = {
           WeatherAppBar(navController = navController,
               title = "About",
               isMainScreen = false,
               icon = Icons.Rounded.ArrowBack,
               elevation = 0.dp,
               city = city){
               navController.popBackStack()
           }
       }) {
           innerPadding ->
           AboutMainScreen(modifier = Modifier.padding(innerPadding),city)
       }

   }
}

@Composable
fun AboutMainScreen(modifier: Modifier,city: String) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = city,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 23.sp,
                fontWeight = FontWeight.W500,
                color = Color.Black)
            Text(text = "${Constants.BASE_URL}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black)
        }


    }
}
