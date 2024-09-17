package com.Gautam.weather.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.Gautam.weather.screens.aboutScreen.WeatherAboutScreen
import com.Gautam.weather.screens.favSecreen.WeatherFavScreen
import com.Gautam.weather.screens.mainScreen.WeatherMainScreen
import com.Gautam.weather.screens.searchScreen.WeatherSearchScreen
import com.Gautam.weather.screens.settingScreen.WeatherSettingScreen
import com.Gautam.weather.screens.splashScreen.WeatherSplashScreen
import com.Gautam.weather.viewmodel.mainViewModel

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController ,
        startDestination = WeatherScreens.SplashScreen.name
    ){

     composable(WeatherScreens.SplashScreen.name)
     {
         WeatherSplashScreen(navController = navController)
     }


        composable(WeatherScreens.FavoriteScreen.name)
        {
            WeatherFavScreen(navController = navController)
        }


        composable(WeatherScreens.SettingScreen.name)
        {
            WeatherSettingScreen(navController = navController)
        }

        val about = WeatherScreens.AboutScreen.name
        composable("$about/{city}", arrayListOf(
            navArgument(name = "city"){
                type = NavType.StringType
            }
        )) { backstack ->
            backstack.arguments?.getString("city").let{
                WeatherAboutScreen(navController = navController,
                    city = it.toString())
            }
        }

        val navToMainScreen = WeatherScreens.MainScreen.name
        composable("$navToMainScreen/{city}", arrayListOf(
            navArgument(name = "city"){
                type = NavType.StringType
            }
        )) { backstack ->
            backstack.arguments?.getString("city").let{
                val viewmodel : mainViewModel = hiltViewModel()
                WeatherMainScreen(navController = navController,
                    city = it,
                    viewmodel = viewmodel)
            }
        }

        composable(WeatherScreens.SearchScreen.name)
        {
            val viewmodel : mainViewModel = hiltViewModel()
            WeatherSearchScreen(navController = navController,
                viewmodel = viewmodel)
        }
    }
}