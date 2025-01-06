package com.empresa.aplicacion

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


class MainActivity : ComponentActivity() {

    @Dao
    interface UserDao {
        @Insert
        suspend fun insert(user: User)

        @Query("SELECT * FROM TablaUsuario")
        suspend fun getAllUsers(): List<User>
    }

    @Entity(tableName = "TablaUsuario")
    data class User(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        val nombreUsario: String,
        val edad: Int
    )

    //La parte de la base de datos no se hacerla ( El punto c. opcional
    // de la 2º parte de la tarea
    companion object
    {
        private const val TAG = "MainActivityLifecycle"
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate called")
        Log.e("TestTag", "Log de prueba onCreate")
        // onLowMemory() //Profe, mi ordenador no es super potente asique no pude hacer pruebas muy fiables de si este metodo funciona
        //requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContent {
            Tarea1_MartinezPerezJavierTheme {
                MyScreen()
            }
        }
    }

    override fun onStart()
    {
        super.onStart()
        Log.d("MainActivityLifecycle", "Log: abriendo ")
    }

    override fun onResume()
    {
        super.onResume()
        Log.d("MainActivityLifecycle", "Log: onResume (todo correcto) ")
    }

    override fun onPause()
    {
        super.onPause()
        Log.d("MainActivityLifecycle", "Log: pausa, se cierra la app o se pone en 2º plano ")
    }

    override fun onStop()
    {
        super.onStop()
        Log.d("MainActivityLifecycle", "Log: parando ")
    }

    override fun onDestroy()
    {
        super.onDestroy()
        Log.d("MainActivityLifecycle", "Log: cerrar app del todo")
    }

    override fun onRestart()
    {
        super.onRestart()
        Log.d("MainActivityLifecycle", "Log: reinicio")
    }

    override fun onLowMemory()
    {
        super.onLowMemory()
        Log.d("MainActivityLifecycle", "Log: onLowMemory PD: no lo probé pq mi pc no es muy potente")
    }


    override fun onConfigurationChanged(newConfig: Configuration)
    {
        super.onConfigurationChanged(newConfig)
        Log.d(TAG, "onConfigurationChanged called - Configuración cambiada")
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
    val items = listOf(
        "\nNombre del usuario: María López (65 años)\nIntereses: Aprender a usar WhatsApp.",
        "Nombre del usuario: Juan Pérez (22 años)\nIntereses: Enseñar tecnología básica.",
        "Nombre del usuario: Sofía Ramírez (30 años)\nIntereses: Conversaciones culturales.",
        "Nombre del usuario: Carlos Díaz (70 años)\nIntereses: Compartir historias de pesca."
    )
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
