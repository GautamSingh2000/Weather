@file:OptIn(ExperimentalMaterial3Api::class)

package com.Gautam.weather.screens.searchScreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.Gautam.weather.data.DataOrExceptions
import com.Gautam.weather.model.responces.WeatherResponse
import com.Gautam.weather.screens.mainScreen.MainContent
import com.Gautam.weather.ui.theme.Blue
import com.Gautam.weather.viewmodel.mainViewModel
import com.Gautam.weather.widigits.WeatherAppBar

@Composable
fun WeatherSearchScreen(navController: NavController, viewmodel: mainViewModel) {
    Scaffold(
        topBar = {
            WeatherAppBar(
                title = "Search",
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                isMainScreen = false,
                navController = navController,
                elevation = 0.dp,
                onButtonClicked = {
                    navController.popBackStack()
                }
            )
        }
    )
    { innerPadding ->
        SearchMainScreen(modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth(),
            viewmodel,
            navController)
    }
}

@Composable
fun SearchMainScreen(
    modifier: Modifier = Modifier,
    viewmodel: mainViewModel,
    navController: NavController
) {
    val searchItem = remember {
        mutableStateOf("")
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBar(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .align(Alignment.CenterHorizontally)){
            Log.e("SrachScreen","search item is $it")
          searchItem.value = it
        }
        if(searchItem.value.isNotEmpty() && searchItem.value.isNotBlank())
        {
            showResult(searchItem.value,viewmodel)
        }else{
            Log.e("weatherSearchScreen","search item is null")
            Box(modifier = Modifier)
        }
    }
}

@Composable
fun showResult(it: String, viewmodel: mainViewModel) {
    val weatherData = produceState<DataOrExceptions<WeatherResponse,Boolean,Exception>>(
        initialValue = DataOrExceptions(isLoading = true)) {
        value = viewmodel.GetWeatherData(it)
    }.value

    MainContent(weatherData.data)
}

@Composable
fun SearchBar(
    modifier: Modifier,
    searchItem: (String) -> Unit = {})
{
    val searchQueryState = rememberSaveable { mutableStateOf("") }
    val keybordController = LocalSoftwareKeyboardController.current
    val valid = remember(searchQueryState.value) { searchQueryState.value.trim().isNotBlank() }
        CommonTextField(
            valueState = searchQueryState,
            placeholder = "Search City",
            onAction = KeyboardActions{
                if(!valid) return@KeyboardActions
                searchItem(searchQueryState.value.trim())
                searchQueryState.value = ""
                keybordController?.hide()
            }
        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTextField(
    valueState: MutableState<String>,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = {
            valueState.value = it
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Blue,
            focusedLabelColor = Blue,
            focusedTextColor = Blue,
            cursorColor = Color.Black
        ),
        shape = RoundedCornerShape(corner = CornerSize(10.dp)),
        singleLine = true,
        maxLines = 1,
        label = { Text(text = placeholder) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction
    )
}
