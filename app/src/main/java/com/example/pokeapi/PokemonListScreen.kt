package com.example.pokeapi

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun PokemonListScreen(navController: NavController, viewModel: PokemonViewModel) {
    // Cargar la lista de Pokémon al iniciar
    LaunchedEffect(Unit) {
        viewModel.fetchPokemonList(limit = 20, offset = 0)
    }

    // Observar la lista de Pokémon desde el ViewModel
    val pokemonList by viewModel.pokemonList.collectAsState()

    // Mostrar la lista en un LazyColumn
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp) // Agregar un poco de espacio alrededor de la lista
    ) {
        items(pokemonList) { pokemon ->
            PokemonCard(pokemon = pokemon, onClick = {
                navController.navigate("pokemonDetails/${pokemon.name}")
            })
        }
    }
}

@Composable
fun PokemonCard(pokemon: PokemonResult, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp) // Espacio entre las tarjetas
            .clickable { onClick() }, // Navegar al detalle del Pokémon
        elevation = CardDefaults.cardElevation(4.dp), // Sombra para el efecto de elevación
        shape = RoundedCornerShape(12.dp), // Bordes redondeados
        colors = CardDefaults.cardColors(
            containerColor = Color(0x807171E0)
        ) // Color de fondo de la tarjeta
    ) {
        Row(
            modifier = Modifier.padding(16.dp), // Espacio interno en la tarjeta
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Aquí se podría agregar una imagen si se tiene una URL de imagen
            Icon(
                imageVector = Icons.Default.Star, // Placeholder para la imagen del Pokémon
                contentDescription = "Icono Pokémon",
                modifier = Modifier
                    .size(40.dp) // Tamaño del icono
                    .padding(end = 16.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer // Color del icono
            )

            Column(
                modifier = Modifier.weight(1f) // El texto ocupa el espacio restante
            ) {
                Text(
                    text = pokemon.name.capitalize(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Tap to see details",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}



