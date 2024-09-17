package com.Gautam.weather.screens.splashScreen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.Gautam.weather.navigation.WeatherScreens
import com.example.weather.R
import kotlinx.coroutines.delay

@Composable
fun WeatherSplashScreen(navController: NavController) {
    DisplaySplashScreen(navController)
}

@Preview
@Composable
fun DisplaySplashScreen(navController: NavController = NavController(LocalContext.current)) {
    val scale = remember { Animatable(0f) }

    LaunchedEffect(key1 = true,
        block = {
        scale.animateTo(
            targetValue = 0.9f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy, // Bouncy spring effect
                stiffness = Spring.StiffnessLow // Low stiffness for slow movements
            )
        )
            delay(2000L)
            navController.popBackStack()
            navController.navigate(WeatherScreens.MainScreen.name+"/Delhi")
    })

    Surface(
        color = Color.White,
        border = BorderStroke(
            width = 1.dp,
            color = Color.Gray
        ),
        shape = CircleShape,
        modifier = Modifier
            .size(300.dp)
            .padding(20.dp)
            .scale(scale.value)
    )
    {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
          Image(
              painter = painterResource(R.drawable.splash_image),
              contentDescription = "Splash Image"
          )
            Text(text = "Find the Sun!",
                fontSize = 17.sp,
                color = Color.Gray,
                fontWeight = FontWeight.W500)
        }
    }
}
