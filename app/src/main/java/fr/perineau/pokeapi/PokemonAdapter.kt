package fr.perineau.pokeapi

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fr.perineau.pokeapi.data.Pokemon
import fr.perineau.pokeapi.data.VolleyInstance
import fr.perineau.pokeapi.databinding.PokemonListBinding

class PokemonAdapter() : ListAdapter<Pokemon, PokemonAdapter.PokemonViewHolder>(DiffPokemon()) {

    class PokemonViewHolder(private var binding: PokemonListBinding,private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Pokemon) {
            binding.name.text = item.name
            binding.root.setOnClickListener { view: View ->
                view.findNavController()
                    .navigate(PokemonsListDirections.actionPokemonsListToPokemonDetails(item.id))
            }
            //binding.pokemonImage.setImageUrl(item.sprite,VolleyInstance.getInstance(context).imageLoader)
        }
        
        companion object {
            fun from(parent: ViewGroup): PokemonViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PokemonListBinding.inflate(layoutInflater, parent, false)
                return PokemonAdapter.PokemonViewHolder(binding,parent.context)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}


class DiffPokemon : DiffUtil.ItemCallback<Pokemon>() {
    override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem.id == oldItem.id
    }

    override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem == newItem
    }

}