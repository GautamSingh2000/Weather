package com.Gautam.weather.screens.mainScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.Gautam.weather.data.DataOrExceptions
import com.Gautam.weather.model.responces.City
import com.Gautam.weather.model.responces.WeatherResponse
import com.Gautam.weather.navigation.WeatherScreens
import com.Gautam.weather.ui.theme.Blue
import com.Gautam.weather.ui.theme.Gray
import com.Gautam.weather.ui.theme.Yellow
import com.Gautam.weather.utils.DateConveter
import com.Gautam.weather.utils.timeStempToDay
import com.Gautam.weather.utils.timeStempToHour
import com.Gautam.weather.viewmodel.mainViewModel
import com.Gautam.weather.widigits.WeatherAppBar
import com.example.weather.R

@Composable
fun WeatherMainScreen(navController: NavController, viewmodel: mainViewModel, city: String?) {
    DisplayMainScreen(navController, viewmodel,city)
}

@Composable
fun DisplayMainScreen(navController: NavController, viewmodel: mainViewModel, city: String?) {
    val WeatherData = produceState<DataOrExceptions<WeatherResponse, Boolean, Exception>>(
        initialValue = DataOrExceptions(isLoading = true)
    ) {
        try {
            value = viewmodel.GetWeatherData("$city")
        }catch (e : Exception)
        {
            Log.e("MainSCreen","exception we got is ${e.message}")
        }
    }.value
    Column {
        if (WeatherData.isLoading == true) {
            CircularProgressIndicator()
        } else {
            if (WeatherData.data.toString().isNotEmpty()) {
                MainScaffold(data = WeatherData.data!!, navController)
                Log.e("WeatherMainScreen", "${WeatherData.data?.city}")
            } else {
                Log.e("WeatherMainScreen", "data is null")
            }
        }

    }
}

@Composable
fun MainScaffold(data: WeatherResponse, navController: NavController) {
    Scaffold(
        topBar = {
            WeatherAppBar(
                title = "${data.city.name}, ${data.city.country}",
                isMainScreen = true,
                elevation = 12.dp,
                navController = navController,
                city = data.city.name,
                onActionClicked = {
                    navController.navigate(WeatherScreens.SearchScreen.name)
                }
            ) {
                Log.e("MainScreen", "back button is clicked !!")
            }
        }
    ) { innerPadding ->
        MainContent(data, modifier = Modifier.padding(innerPadding))
    }
}

@Preview
@Composable
fun MainContent(
    data: WeatherResponse? = null,
    modifier: Modifier = Modifier,
) {
    val sunset = data?.list?.get(0)?.sunset
    val sunrise = data?.list?.get(0)?.sunrise
    val date = data?.list?.get(0)?.dt

    Log.e("MainContent","data we get is ${data?.city}")
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (date != null) {
            Text(
                text = DateConveter(date),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(6.dp),
                color = Color.Black
            )
        }
        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp),
            shape = CircleShape,
            color = Yellow
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherImage(data?.list?.get(0)?.weather?.get(0)?.icon)
                Text(
                    text = "${data?.list?.get(0)?.temp?.day}",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = "${data?.list?.get(0)?.weather?.get(0)?.main}",
                    style = TextStyle(
                        fontStyle = FontStyle.Italic,
                        fontSize = 15.sp
                    ),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        wind_pressure_humidity(
            humidity = data?.list?.get(0)?.humidity.toString(),
            pressure = data?.list?.get(0)?.pressure.toString(),
            windSpeed = data?.list?.get(0)?.speed.toString()
        )

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = 5.dp,
                    end = 5.dp,
                    start = 5.dp
                ),
            thickness = 0.8.dp,
            color = Color.LightGray
        )

        if (sunrise != null && sunset != null) {
            Suntime(
                sunRise = timeStempToHour(sunrise),
                sunSet = timeStempToHour(sunset)
            )
        }

        Surface(
            shape = RoundedCornerShape(10.dp),
            color = Gray,
            modifier = Modifier.padding(horizontal = 5.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 6.dp,
                        vertical = 1.dp
                    )
            ) {
                if(data!=null)
                {
                    items(items = data.list)
                    {
                        DetailedRow(
                            day = timeStempToDay(it.dt),
                            image = it.weather.get(0).icon,
                            high = it.temp.max.toString(),
                            low = it.temp.min.toString(),
                            weather = it.weather.get(0).description
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun DetailedRow(
    day: String = " - - ",
    image : String = "",
    high: String = " - - ",
    low: String = " - - ",
    weather: String = " - - ",
    OncardClick: () -> Unit = {},
) {
    Card(
        shape = RoundedCornerShape(
            topEndPercent = 0,
            topStartPercent = 30,
            bottomEndPercent = 30,
            bottomStartPercent = 30
        ),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        onClick = {
            OncardClick()
        }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(
                    vertical = 20.dp,
                    horizontal = 10.dp
                )
                .fillMaxWidth()
        ) {
            Text(
                text = "$day", style = TextStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.W300, fontSize = 20.sp
                )
            )

            AsyncImage(model = ImageRequest.Builder(LocalContext.current)
                .data("https://openweathermap.org/img/wn/$image.png")
                .crossfade(false)
                .placeholder(R.drawable.splash_image)
                .error(R.drawable.error)
                .listener(
                    onError = { _, result -> Log.e("ImageError", "Failed to load image: ${result.throwable}") },
                    onSuccess = { _, _ -> Log.d("ImageSuccess", "Image loaded successfully!") }
                )
                .build(),
                modifier = Modifier.size(45.dp),
                contentDescription = null)

            Surface(
                color = Yellow,
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    text = weather,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp)
                )
            }

            Row {
                Text(
                    text = "$high°", style = TextStyle(
                        color = Blue,
                        fontWeight = FontWeight.W500,
                        fontSize = 20.sp
                    )
                )
                Text(
                    text = "$low°", style = TextStyle(
                        color = Color.LightGray,
                        fontWeight = FontWeight.W500,
                        fontSize = 20.sp
                    ),
                    modifier = Modifier.padding(start = 5.dp)
                )

            }
        }
    }

}


@Composable
fun Suntime(
    sunRise: String = "",
    sunSet: String = "",
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, bottom = 15.dp, end = 10.dp, start = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = sunRise,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 5.dp)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = sunSet,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(end = 5.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.sunset), contentDescription = null,
                modifier = Modifier.size(20.dp)
            )

        }
    }
}

@Composable
fun wind_pressure_humidity(
    humidity: String = "",
    pressure: String = "",
    windSpeed: String = "",
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 15.dp, end = 10.dp, start = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = null,
                modifier = Modifier.size(25.dp)
            )
            Text(
                text = "$humidity %",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                fontWeight = FontWeight.W400,
                modifier = Modifier.padding(start = 5.dp)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.pressure), contentDescription = null,
                modifier = Modifier.size(25.dp)
            )
            Text(
                text = "$pressure psi",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                fontWeight = FontWeight.W400,
                modifier = Modifier.padding(start = 5.dp)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.wind_speed),
                contentDescription = null,
                modifier = Modifier.size(25.dp)
            )
            Text(
                text = "$windSpeed mph",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                fontWeight = FontWeight.W400,
                modifier = Modifier.padding(start = 5.dp)
            )
        }
    }

}

@Composable
fun WeatherImage(icon: String?) {
    val image = if (icon != null) {
        "https://openweathermap.org/img/wn/$icon.png"
    } else {
        "https://openweathermap.org/img/wn/01d.png"
    }
    Log.e("mainScreen", "icons is $image")
    AsyncImage(model = ImageRequest.Builder(LocalContext.current)
        .data(image)
        .crossfade(true)
        .placeholder(R.drawable.splash_image)
        .error(R.drawable.error)
        .listener(
            onError = { _, result -> Log.e("ImageError", "Failed to load image: ${result.throwable}") },
            onSuccess = { _, _ -> Log.d("ImageSuccess", "Image loaded successfully!") }
        )
        .build(),
        modifier = Modifier.size(70.dp),
        contentDescription = null)
}
