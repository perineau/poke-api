package fr.perineau.pokeapi

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import fr.perineau.pokeapi.api.PokemonApi
import fr.perineau.pokeapi.api.VolleyInstance
import fr.perineau.pokeapi.data.Pokemon
import fr.perineau.pokeapi.databinding.FragmentPokemonDetailsBinding
import fr.perineau.pokeapi.databinding.FragmentPokemonsListBinding

class PokemonDetails : Fragment() {

    private var _binding: FragmentPokemonDetailsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val args: PokemonDetailsArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPokemonDetailsBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.progressBar.visibility = View.VISIBLE
        val pokemonApi = PokemonApi(requireContext())
        pokemonApi.getPokemonDetails(args.pokemonId) { pokemonDetails ->
            binding.pokemon = pokemonDetails
            binding.pokemonImage.setImageBitmap(pokemonDetails.sprite)
            binding.progressBar.visibility = View.GONE
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
