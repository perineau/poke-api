package fr.perineau.pokeapi.api

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import fr.perineau.pokeapi.data.Pokemon
import fr.perineau.pokeapi.data.PokemonDetails


class PokemonApi(private val context: Context) {
    private val url = "https://pokeapi.co/api/v2/"

    private val volley = VolleyInstance.getInstance(context)

    fun getPokemonCount(callback: (Int) -> Unit) {
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, "${url}/pokemon?limit=%d".format(1), null,
            { response ->
                callback(response.getString("count").toInt())
            },
            { error ->
                Log.e("getPokemonCount", error.message.toString())
                Toast.makeText(context, error.message.toString(), Toast.LENGTH_LONG).show()
            }
        )
        volley.addToRequestQueue(jsonObjectRequest)
    }

    fun getPokemons(number: Int, callback: (List<Pokemon>) -> Unit) {

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, "${url}/pokemon?limit=%d".format(number), null,
            { response ->
                val pokemons = response.getJSONArray("results")
                val pokemonList = arrayListOf<Pokemon>()
                for (index in 0 until pokemons.length()) {
                    val pokemon = pokemons.getJSONObject(index)
                    val id = pokemon.getString("url").split("/").dropLast(1).last().toInt()
                    pokemonList.add(
                        Pokemon(
                            pokemon.getString("name"),
                            id,
                            null
                        )
                    )
                }

                callback(pokemonList)
            },
            { error ->
                Log.e("getPokemons", error.message.toString())
                Toast.makeText(context, error.message.toString(), Toast.LENGTH_LONG).show()
            }
        )
        volley.addToRequestQueue(jsonObjectRequest)
    }

    fun getPokemonDetails(id: Int, callback: (PokemonDetails) -> Unit) {

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, "${url}/pokemon/%d".format(id), null,
            { response ->
                val sprites = response.getJSONObject("sprites")
                val spriteUrl = sprites.getString("front_default")
                callback(PokemonDetails(spriteUrl))

            },
            { error ->
                Log.e("getPokemonDetails", error.message.toString())
                Toast.makeText(context, error.message.toString(), Toast.LENGTH_LONG).show()
            }
        )
        volley.addToRequestQueue(jsonObjectRequest)
    }


}