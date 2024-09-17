package com.Gautam.weather.widigits

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.Gautam.weather.navigation.WeatherScreens


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    navController: NavController,
    elevation: Dp = 0.dp,
    city : String = "" ,
    onActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {},
) {
    var ShowDialogState by remember {
        mutableStateOf(false)
    }
    if (ShowDialogState) {
        showDialog(navController, ShowDialogState,city)
    }

    Surface(
        shadowElevation = elevation,
        shape = RoundedCornerShape(corner = CornerSize(10.dp)),
        modifier = Modifier.padding(horizontal = 5.dp, vertical = 5.dp)
            .fillMaxWidth(),
        color = Color.Transparent
    ) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(Color.White),
            title = {
                Text(
                    text = "$title",
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.W400
                    )
                )
            },
            actions = {
                if (isMainScreen) {
                    IconButton(onClick = { onActionClicked() }) {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = "Search Icon",
                            modifier = Modifier
                                .padding(end = 10.dp)
                        )
                    }

                    IconButton(onClick = {
                        ShowDialogState = !ShowDialogState
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.MoreVert,
                            contentDescription = "More Icon",
                            modifier = Modifier.padding(end = 10.dp)
                        )
                    }
                } else {
                    Box(modifier = Modifier)
                }
            },
            navigationIcon = {
                if (icon != null && !isMainScreen) {
                    Icon(imageVector = icon,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .clickable {
                                onButtonClicked()
                            })
                } else {
                    Box(modifier = Modifier)
                }
            })
    }
}

@Composable
fun showDialog(navController: NavController, showDialogState: Boolean, city: String) {
    val expandable = remember {
        mutableStateOf(true)
    }
    val itemsInMenu = listOf("About", "Favourite", "Setting")


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .absolutePadding(8.dp)
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 60.dp, right = 20.dp),
    ) {
        DropdownMenu(
            expanded = expandable.value, onDismissRequest = { expandable.value = false },
            modifier = Modifier
                .width(140.dp)
                .background(Color.White)
        ) {
            itemsInMenu.forEachIndexed { index, item ->
                val icon = when (item) {
                    "About" -> {
                        Icons.Rounded.Info
                    }

                    "Favourite" -> {
                        Icons.Rounded.Favorite
                    }

                    else -> {
                        Icons.Rounded.Settings
                    }
                }

                menuDetail(item = item, icon = icon) {
                    expandable.value = false
                    when (item) {
                        "About" -> {
                            navController.navigate(WeatherScreens.AboutScreen.name+"/$city")
                        }

                        "Favourite" -> {
                            navController.navigate(WeatherScreens.FavoriteScreen.name)
                        }

                        else -> {
                            navController.navigate(WeatherScreens.SettingScreen.name)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun menuDetail(item: String, icon: ImageVector, onItemClick: () -> Unit = {}) {
    Surface(color = Color.White,
        modifier = Modifier.clickable { onItemClick() }) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .absolutePadding(top = 2.dp, right = 5.dp, left = 5.dp)
                .fillMaxWidth()
        ) {
            Image(
                imageVector = icon, contentDescription = null,
                modifier = Modifier.absolutePadding(right = 5.dp, left = 5.dp),
                colorFilter = ColorFilter.tint(Color.Gray)
            )
            Text(text = item, color = Color.Gray)
        }
    }
}
