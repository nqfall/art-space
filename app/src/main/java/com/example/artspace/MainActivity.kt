package com.example.artspace

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceScreen()
        }
    }
}

@Composable
fun ArtSpaceScreen() {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    if (isPortrait) {
        PortraitLayout()
    } else {
        LandscapeLayout()
    }
}

// ---------- Портретный режим ----------
@Composable
fun PortraitLayout() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImageWithFrame()
        Spacer(modifier = Modifier.height(16.dp))
        DescriptionBox()
        Spacer(modifier = Modifier.height(16.dp))
        NavigationButtons()
    }
}

// ---------- Ландшафтный режим ----------
@Composable
fun LandscapeLayout() {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ImageWithFrame()
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DescriptionBox()
            Spacer(modifier = Modifier.height(16.dp))
            NavigationButtons()
        }
    }
}

// ---------- Компоненты ----------

// 📌 Изображение с белой рамкой и тенью
@Composable
fun ImageWithFrame() {
    Box(
        modifier = Modifier
            .size(300.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = images[currentIndex].first),
            contentDescription = "Artwork",
            modifier = Modifier.fillMaxSize()
        )
    }
}

// 📌 Описание изображения
@Composable
fun DescriptionBox() {
    Column(
        modifier = Modifier
            .background(Color(0xFFF0F0F0), shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = images[currentIndex].second,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = images[currentIndex].third,
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}

// 📌 Кнопки "Назад" и "Вперед" (реализована карусель)
var currentIndex by mutableStateOf(0)

val images = listOf(
    Triple(R.drawable.img1, "Sailing Under the Bridge", "Kat Kuan (2017)"),
    Triple(R.drawable.img2, "Sunset Over the Ocean", "Liam Johnson (2020)"),
    Triple(R.drawable.img3, "City Lights at Night", "Sophia Lee (2019)")
)

@Composable
fun NavigationButtons() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = { currentIndex = (currentIndex - 1 + images.size) % images.size }
        ) {
            Text("Previous")
        }
        Button(
            onClick = { currentIndex = (currentIndex + 1) % images.size }
        ) {
            Text("Next")
        }
    }
}
