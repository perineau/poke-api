package fr.perineau.pokeapi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.navArgs
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest

class PokemonDetails : Fragment() {



    //https://pokeapi.co/api/v2/pokemon/{arg.pokemon.id}

    val args: PokemonDetailsArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args.pokemonId
    }

    private fun jsonParse() {
        val url = "https://pokeapi.co/api/v2/pokemon/{pokemonId}"
        /*val request = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
                response ->try {
        })*/

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pokemon_details, container, false)
    }
}