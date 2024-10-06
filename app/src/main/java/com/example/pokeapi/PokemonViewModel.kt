package com.example.pokeapi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PokemonViewModel(private val repository: PokemonRepository) : ViewModel() {
    private val _pokemonList = MutableStateFlow<List<PokemonResult>>(emptyList())
    val pokemonList: StateFlow<List<PokemonResult>> get() = _pokemonList

    private val _pokemonDetails = MutableStateFlow<PokemonDetails?>(null)
    val pokemonDetails: StateFlow<PokemonDetails?> get() = _pokemonDetails

    fun fetchPokemonList(limit: Int, offset: Int) {
        viewModelScope.launch {
            val response = repository.getPokemonList(limit, offset)
            _pokemonList.value = response.results
        }
    }

    fun fetchPokemonDetails(name: String) {
        viewModelScope.launch {
            val response = repository.getPokemonDetails(name)
            _pokemonDetails.value = response
        }
    }
}
