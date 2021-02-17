package fr.perineau.pokeapi

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import fr.perineau.pokeapi.data.Pokemon
import fr.perineau.pokeapi.data.VolleyInstance
import fr.perineau.pokeapi.databinding.FragmentPokemonsListBinding


class PokemonsList : Fragment() {

    private var _binding: FragmentPokemonsListBinding ? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPokemonsListBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.progressBar.visibility = View.VISIBLE
        val adapter = PokemonAdapter()
        binding.pokemonList.adapter = adapter
        binding.pokemonList.layoutManager = LinearLayoutManager(context)


        val url = "https://pokeapi.co/api/v2/pokemon?limit=%d"

        val pokemonList = arrayListOf<Pokemon>()

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url.format(1), null,
            { response ->
                val jsonObjectRequest2 = JsonObjectRequest(Request.Method.GET, url.format(response.getInt("count")), null,
                    { response ->
                        val pokemonListResponse = response.getJSONArray("results")
                        for (pokemonIndex in 0 until pokemonListResponse.length()){
                            val pokemon = pokemonListResponse.getJSONObject(pokemonIndex)
                            pokemonList.add(
                                Pokemon(pokemon.getString("name"),
                                        pokemon.getString("url").split("/").dropLast(1).last().toInt(),null)
                            )

                        }

                        Log.e("test", pokemonList.toString())
                        binding.progressBar.visibility = View.GONE
                        adapter.submitList(pokemonList)
                    },
                    { error ->
                        Toast.makeText(context,error.message,Toast.LENGTH_LONG).show()
                        binding.progressBar.visibility = View.GONE
                    })
                VolleyInstance.getInstance(requireContext()).addToRequestQueue(jsonObjectRequest2)
            },
            { error ->
                Toast.makeText(context,error.message,Toast.LENGTH_LONG).show()
                binding.progressBar.visibility = View.GONE
            }
        )
        VolleyInstance.getInstance(requireContext()).addToRequestQueue(jsonObjectRequest)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}