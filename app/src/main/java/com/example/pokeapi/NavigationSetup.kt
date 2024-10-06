package com.example.pokeapi

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavigationSetup(navController: NavHostController, viewModel: PokemonViewModel) {
    NavHost(navController = navController, startDestination = "pokemonList") {
        composable("pokemonList") {
            PokemonListScreen(navController, viewModel)
        }
        composable("pokemonDetails/{name}") { backStackEntry ->
            val pokemonName = backStackEntry.arguments?.getString("name") ?: ""
            PokemonDetailsScreen(viewModel, pokemonName)
        }
    }
}
