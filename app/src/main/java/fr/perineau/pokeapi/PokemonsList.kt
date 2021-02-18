package fr.perineau.pokeapi

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import fr.perineau.pokeapi.api.PokemonApi
import fr.perineau.pokeapi.data.Pokemon
import fr.perineau.pokeapi.databinding.FragmentPokemonsListBinding


class PokemonsList : Fragment() {

    private var _binding: FragmentPokemonsListBinding? = null

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

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val adapter = PokemonAdapter()

        val oldSearch = sharedPref?.getString("search", "")

        binding.progressBar.visibility = View.VISIBLE
        binding.pokemonList.adapter = adapter
        binding.pokemonList.layoutManager = LinearLayoutManager(context)
        val api = PokemonApi(requireContext())

        var pokemonList = ArrayList<Pokemon>()
        binding.search.doOnTextChanged { text, start, before, count ->
            search(text, adapter, pokemonList)
            if (sharedPref != null) {
                with(sharedPref.edit()) {
                    putString("search", text.toString())
                    apply()
                }
            }


        }
        api.getPokemonCount { count ->
            api.getPokemons(count) { list ->
                pokemonList = ArrayList(list.sortedWith(compareBy { it.name }))
                adapter.submitList(pokemonList)

                if (oldSearch != null) {
                    binding.search.setText(oldSearch)
                    search(oldSearch, adapter, pokemonList)
                }

                binding.progressBar.visibility = View.GONE
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun search(text: CharSequence?, adapter: PokemonAdapter, pokemonList: List<Pokemon>) {
        if (text != null) {
            adapter.submitList(pokemonList.filter { pokemon -> pokemon.name.startsWith(text) })
        } else {
            adapter.submitList(pokemonList)
        }
    }
}
