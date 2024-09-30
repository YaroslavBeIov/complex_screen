package com.example.complex_screen

import android.content.Intent
import android.net.Uri
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.complex_screen.ui.theme.Complex_screenTheme
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.PreviewParameter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Complex_screenTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ProfileScreen(modifier = Modifier.padding(innerPadding), context = this)
                }
            }
        }
    }
}

@Composable
fun ProfileScreen(modifier: Modifier = Modifier, context: ComponentActivity) {
    var selectedTab by remember { mutableStateOf(TabType.NONE) }
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState), // Добавляем прокрутку к колонне
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "My Profile",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp),
            fontWeight = FontWeight.Bold
        )

        // Контейнер с изображением пользователя и фоновым изображением
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f) // Соотношение сторон 1:1 для квадратного изображения
                .padding(bottom = 16.dp)
        ) {
            // Фоновое изображение
            Image(
                painter = painterResource(id = R.drawable.zal), // Замените на ваше изображение фона
                contentDescription = "Background Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Фотография пользователя
            Image(
                painter = painterResource(id = R.drawable.my_photo), // Замените на ваше изображение
                contentDescription = "User Photo",
                modifier = Modifier.fillMaxSize()
            )
        }

        // Информация о пользователе
        Text(text = "Имя: Билли Харрингтон", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Возраст: 55 лет", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Специализация: Киноактёр", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(24.dp))

        // Кнопки для выбора вкладки
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { selectedTab = TabType.PHOTO }) {
                Text("Фото")
            }
            Button(onClick = { selectedTab = TabType.PROJECTS }) {
                Text("Проекты")
            }
            Button(onClick = { selectedTab = TabType.ACHIEVEMENTS }) {
                Text("Достижения")
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        // Контент в зависимости от нажатой кнопки
        when (selectedTab) {
            TabType.PHOTO -> {
                Column {
                    Text("Друг сфоткал пока я отдыхал", fontWeight = FontWeight.Bold)
                    GalleryImage(R.drawable.v_bare, "Photo 1")
                    Text("Друг сфоткал пока я ехал на байке", fontWeight = FontWeight.Bold)
                    GalleryImage(R.drawable.baik, "Photo 2")
                    Text("Друг сфоткал пока я переодевался", fontWeight = FontWeight.Bold)
                    GalleryImage(R.drawable.ya, "Photo 3")
                }
            }
            TabType.PROJECTS -> {
                Column {
                    Text("Скачать проекты:", fontWeight = FontWeight.Bold)
                    Button(onClick = { downloadFile("https://example.com/app1.apk", context) }) {
                        Text("Скачать проект качалка (APK)")
                    }
                    Button(onClick = { downloadFile("https://example.com/app2.apk", context) }) {
                        Text("Скачать проект качалочка (APK)")
                    }
                    Button(onClick = { downloadFile("https://example.com/app3.apk", context) }) {
                        Text("Скачать проект в качалке (APK)")
                    }
                }
            }
            TabType.ACHIEVEMENTS -> {
                Column {
                    Text(text = "Босс качалки 2010", fontWeight = FontWeight.Bold)
                    Text(text = "Босс качалки 2012", fontWeight = FontWeight.Bold)
                    Text(text = "Босс качалки 2018", fontWeight = FontWeight.Bold)
                }
            }
            TabType.NONE -> {}
        }
    }
}

fun downloadFile(url: String, context: ComponentActivity) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    context.startActivity(intent)
}

enum class TabType {
    NONE,
    PHOTO,
    PROJECTS,
    ACHIEVEMENTS
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    Complex_screenTheme {
        ProfileScreen(context = object : ComponentActivity() {})
    }
}

@Composable
fun GalleryImage(imageRes: Int, contentDescription: String) {
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = contentDescription,
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp) // Установите фиксированную высоту для изображений
            .padding(bottom = 8.dp),
        contentScale = ContentScale.Crop // Убедитесь, что изображение не обрезается
    )
}
