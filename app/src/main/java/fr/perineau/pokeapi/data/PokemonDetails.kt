package fr.perineau.pokeapi.data

import android.graphics.Bitmap

data class PokemonDetails(var name: String, val sprite: Bitmap?, val height: String, val weight: String)
