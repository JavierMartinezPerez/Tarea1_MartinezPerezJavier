package com.empresa.aplicacion

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import com.empresa.aplicacion.ui.theme.Tarea1_MartinezPerezJaviTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*
profe, me he tirado mas horas de las que me gustaria admitir haciendo esta tarea
por mas que lo he intentado no consigo hacer un boton que al pulsarlo me muestre
un label con la consulta CRUD que hayas escogido, ya no me queda mas tiempo asique
aunque la base de datos funcione no hay boton para comrpboarlo
 */


// Entidad Usuario
@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val edad: Int,
    val intereses: String
)

// DAO de Usuario
@Dao
interface UsuarioDao {
    @Insert
    fun insertar(usuario: Usuario?)

    @Query("SELECT * FROM usuarios")
    fun obtenerTodos(): List<Usuario?>?

    @Update
    fun actualizar(usuario: Usuario?)

    @Delete
    fun eliminar(usuario: Usuario?)
}

// Base de datos
@Database(entities = [Usuario::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

class MainActivity : ComponentActivity() {
    private lateinit var db: AppDatabase
    private lateinit var usuarioDao: UsuarioDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = AppDatabase.getDatabase(this)
        usuarioDao = db.usuarioDao()

        // Ejemplo de cómo insertar un usuario
        lifecycleScope.launch(Dispatchers.IO) {
            val usuario = Usuario(nombre = "María López", edad = 65, intereses = "Aprender a usar WhatsApp")
            usuarioDao.insertar(usuario)
            val usuarios = usuarioDao.obtenerTodos()
            if (usuarios != null) {
                usuarios.forEach {
                    Log.d("Usuario", it.toString())
                }
            }
        }

        setContent {
            Tarea1_MartinezPerezJaviTheme {
                MyScreen()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("MainActivityLifecycle", "Log: abriendo")
    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivityLifecycle", "Log: onResume (todo correcto)")
    }

    override fun onPause() {
        super.onPause()
        Log.d("MainActivityLifecycle", "Log: pausa, se cierra la app o se pone en 2º plano")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MainActivityLifecycle", "Log: parando")
    }

    override fun onDestroy() {
         super.onDestroy()
        Log.d("MainActivityLifecycle", "Log: cerrar app del todo")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("MainActivityLifecycle", "Log: reinicio")
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Log.d("MainActivityLifecycle", "Log: onLowMemory PD: no lo probé pq mi pc no es muy potente")
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