package com.example.pokeapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokeapi.ui.theme.PokeAPITheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokeAPITheme {
                ProgPrincipal()
            }

        }
    }
}

@Composable
fun ProgPrincipal() {
    val navController = rememberNavController()

    // Configuración del ViewModel (similar a tu implementación de Retrofit)
    val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val service = retrofit.create(PokemonApiService::class.java)
    val repository = PokemonRepository(service)
    val viewModelFactory = PokemonViewModelFactory(repository)
    val viewModel: PokemonViewModel = viewModel(factory = viewModelFactory)

    Scaffold(
        topBar = { BarraSuperior() },  // Barra superior
        bottomBar = { BarraInferior(navController) },  // Barra de navegación inferior
        content = { paddingValues ->
            Contenido(paddingValues, navController, viewModel) // Contenido que se muestra
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraSuperior() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Pokémon Explorer",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun BarraInferior(navController: NavHostController) {
    NavigationBar(containerColor = Color.LightGray) {
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") },
            selected = navController.currentDestination?.route == "inicio",
            onClick = { navController.navigate("inicio") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.List, contentDescription = "Pokémon") },
            label = { Text("Pokémon") },
            selected = navController.currentDestination?.route == "pokemonList",
            onClick = { navController.navigate("pokemonList") }
        )
    }
}

@Composable
fun Contenido(
    paddingValues: PaddingValues,
    navController: NavHostController,
    viewModel: PokemonViewModel
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)) {
        NavHost(navController = navController, startDestination = "inicio") {
            composable("inicio") { ScreenInicio() }
            composable("pokemonList") { PokemonListScreen(navController, viewModel) }
            composable("pokemonDetails/{pokemonName}") { backStackEntry ->
                val pokemonName = backStackEntry.arguments?.getString("pokemonName")
                pokemonName?.let { PokemonDetailsScreen(viewModel, it) }
            }
        }
    }
}

@Composable
fun ScreenInicio() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Imagen central
            Image(
                painter = painterResource(id = R.drawable.poke_logo), // Reemplaza con el ID de tu imagen
                contentDescription = "Logo de Pokémon",
                modifier = Modifier
                    .size(200.dp) // Tamaño de la imagen
                    .clip(CircleShape) // Hace la imagen circular (opcional)
                    .border(2.dp, Color.Gray, CircleShape) // Borde alrededor de la imagen (opcional)
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Texto debajo de la imagen
            Text(
                text = "¡Bienvenido al Pokémon Explorer!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}


