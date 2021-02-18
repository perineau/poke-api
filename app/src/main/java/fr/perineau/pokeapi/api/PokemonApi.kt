package fr.perineau.pokeapi.api

import android.R.attr.*
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import android.widget.ImageView.ScaleType
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.ImageRequest
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
                val url = sprites.getString("front_default")
                val name = response.getString("name")
                val height = response.getString("height")
                val weight = response.getString("weight")
                val types = response.getJSONArray("types")
                val typesNames = arrayListOf<String>()
                for (index in 0 until types.length()) {
                    val type = types.getJSONObject(index)
                    val typeName = type.getJSONObject("type").getString("name")
                    typesNames.add(typeName)
                }

                val request = ImageRequest(
                    url, { response ->
                        val pokemon = PokemonDetails(name, response, height, weight, typesNames)
                        callback(pokemon)
                    }, 0,
                    0, ScaleType.CENTER_CROP, Bitmap.Config.RGB_565, { error ->
                        Toast.makeText(context, "Pas d'image", Toast.LENGTH_SHORT).show()
                    }
                )
                volley.addToRequestQueue(request)

            },
            { error ->
                Log.e("getPokemonDetails", error.message.toString())
                Toast.makeText(context, error.message.toString(), Toast.LENGTH_LONG).show()
            }
        )
        volley.addToRequestQueue(jsonObjectRequest)

    }
}