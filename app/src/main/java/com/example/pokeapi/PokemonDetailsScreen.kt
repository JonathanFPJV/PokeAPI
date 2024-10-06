package com.example.pokeapi

import android.media.MediaPlayer
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

    Column(modifier = Modifier.padding(16.dp)) {
        pokemonDetails?.let { pokemon ->
            Text(text = "Nombre: ${pokemon.name.capitalize()}", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Experiencia base: ${pokemon.base_experience}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Altura: ${pokemon.height} dm")
            Text(text = "Peso: ${pokemon.weight} hg")

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Habilidades:", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            pokemon.abilities.forEach {
                val hiddenText = if (it.is_hidden) " (Oculta)" else ""
                Text(text = "- ${it.ability.name.capitalize()}$hiddenText", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Tipos:", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            pokemon.types.forEach {
                Text(text = "- ${it.type.name.capitalize()}", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Sonidos:", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            Row {
                TextButton(onClick = { playSound(pokemon.cries.latest) }) {
                    Text("Último sonido")
                }
                TextButton(onClick = { playSound(pokemon.cries.legacy) }) {
                    Text("Sonido clásico")
                }
            }
        } ?: run {
            Text(text = "Cargando detalles...")
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




