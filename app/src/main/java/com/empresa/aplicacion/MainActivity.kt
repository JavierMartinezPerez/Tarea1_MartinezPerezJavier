package com.empresa.aplicacion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.empresa.aplicacion.ui.theme.Tarea1_MartinezPerezJavierTheme
import com.empresa.aplicacion.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Tarea1_MartinezPerezJavierTheme {
                MyScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScreen() {
    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = { Text("Conecta2: Jóvenes y Mayores") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color(0xFF00BCD4))
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        MainContent(Modifier.padding(innerPadding))
    }
}

@Composable
fun MainContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Texto descriptivo
        Text(
            text = "Conecta2: Jóvenes y Mayores es una aplicación que une a personas mayores con jóvenes para actividades como aprender tecnología, compartir historias y hobbies. Fomenta el intercambio cultural y genera lazos intergeneracionales mientras combate la soledad..",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Imagen
        Image(
            painter = painterResource(id = R.drawable.chico_ayudando_a_viejo),
            contentDescription = "Joven ayudando a viejo con el movil",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(bottom = 16.dp)
        )

        // Lista de elementos
        SampleList()
    }
}

@Composable
fun SampleList() {
    val items = listOf("\nNombre del usuario: María López (65 años)\n" +
            "Intereses: Aprender a usar WhatsApp.", "Nombre del usuario: Juan Pérez (22 años)\n" +
            "Intereses: Enseñar tecnología básica.", "Nombre del usuario: Sofía Ramírez (30 años)\n" +
            "Intereses: Conversaciones culturales.", "Nombre del usuario: Carlos Díaz (70 años)\n" +
            "Intereses: Compartir historias de pesca.")
    LazyColumn {
        items(items.size) { index ->
            Text(
                text = items[index],
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMyScreen() {
    Tarea1_MartinezPerezJavierTheme {
        MyScreen()
    }
}
