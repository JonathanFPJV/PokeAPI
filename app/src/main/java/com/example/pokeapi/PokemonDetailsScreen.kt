package com.example.pokeapi

import android.media.MediaPlayer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Height
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun PokemonDetailsScreen(viewModel: PokemonViewModel, pokemonName: String) {
    // Cargar los detalles del Pokémon
    LaunchedEffect(Unit) {
        viewModel.fetchPokemonDetails(pokemonName)
    }

    // Observar los detalles
    val pokemonDetails by viewModel.pokemonDetails.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        pokemonDetails?.let { pokemon ->
            // Sección del Nombre
            Text(
                text = pokemon.name.capitalize(),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp),
                color = MaterialTheme.colorScheme.primary
            )

            // Card para mostrar estadísticas básicas como experiencia, altura y peso
            PokemonStatsCard(pokemon = pokemon)

            Spacer(modifier = Modifier.height(16.dp))

            // Card para las habilidades del Pokémon
            PokemonAbilitiesCard(pokemon = pokemon)

            Spacer(modifier = Modifier.height(16.dp))

            // Card para los tipos del Pokémon
            PokemonTypesCard(pokemon = pokemon)

            Spacer(modifier = Modifier.height(16.dp))

            // Sonidos del Pokémon con botones de acción
            PokemonSoundsSection(pokemon = pokemon)
        } ?: run {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}

@Composable
fun PokemonStatsCard(pokemon: PokemonDetails) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Estadísticas Básicas",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Star, contentDescription = "Experiencia")
                Text(text = "Experiencia: ${pokemon.base_experience}", style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Height, contentDescription = "Altura")
                Text(text = "Altura: ${pokemon.height} dm", style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.FitnessCenter, contentDescription = "Peso")
                Text(text = "Peso: ${pokemon.weight} hg", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
fun PokemonAbilitiesCard(pokemon: PokemonDetails) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Habilidades",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            pokemon.abilities.forEach {
                val hiddenText = if (it.is_hidden) " (Oculta)" else ""
                Text(
                    text = "- ${it.ability.name.capitalize()}$hiddenText",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun PokemonTypesCard(pokemon: PokemonDetails) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Tipos",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            pokemon.types.forEach {
                Text(
                    text = "- ${it.type.name.capitalize()}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun PokemonSoundsSection(pokemon: PokemonDetails) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Sonidos",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                TextButton(onClick = { playSound(pokemon.cries.latest) }) {
                    Text("Último sonido")
                }

                TextButton(onClick = { playSound(pokemon.cries.legacy) }) {
                    Text("Sonido clásico")
                }
            }
        }
    }
}

fun playSound(url: String) {
    // Función para reproducir sonidos
    val mediaPlayer = MediaPlayer().apply {
        setDataSource(url)
        prepare()
        start()
    }
}




