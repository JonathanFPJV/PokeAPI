package com.example.pokeapi

class PokemonRepository(private val service: PokemonApiService) {
    suspend fun getPokemonList(limit: Int, offset: Int) = service.getPokemonList(limit, offset)

    suspend fun getPokemonDetails(name: String) = service.getPokemonDetails(name)
}
