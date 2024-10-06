package com.example.pokeapi

data class PokemonListResponse(
    val results: List<PokemonResult>
)

data class PokemonResult(
    val name: String,
    val url: String
)

data class PokemonDetails(
    val name: String,
    val base_experience: Int,
    val abilities: List<PokemonAbility>,
    val height: Int,
    val weight: Int,
    val types: List<PokemonType>,
    val cries: PokemonCries
)

data class PokemonAbility(
    val ability: AbilityInfo,
    val is_hidden: Boolean,
    val slot: Int
)

data class AbilityInfo(
    val name: String,
    val url: String
)

data class PokemonType(
    val slot: Int,
    val type: TypeInfo
)

data class TypeInfo(
    val name: String,
    val url: String
)

data class PokemonCries(
    val latest: String,
    val legacy: String
)

