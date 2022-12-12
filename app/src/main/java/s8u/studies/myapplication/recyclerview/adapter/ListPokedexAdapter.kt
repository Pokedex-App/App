package s8u.studies.myapplication.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import s8u.studies.myapplication.R
import s8u.studies.myapplication.api.pokemonEndpoint
import s8u.studies.myapplication.model.Pokedex
import s8u.studies.myapplication.model.Pokemon_entries

class ListPokedexAdapter(
    private val context: Context,
    private val pokemonEntries: ArrayList<Pokemon_entries>,
    private val onListenerPokedex: OnListenerPokedex,
) : RecyclerView.Adapter<ListPokedexAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView: ConstraintLayout = itemView.findViewById(R.id.container_view)
        fun bind(pokemonEntries: Pokemon_entries) {
            val id = itemView.findViewById<TextView>(R.id.pokedex_id)
            id.text = pokemonEntries.id.toString()
            val name = itemView.findViewById<TextView>(R.id.pokedex_name)
            name.text = pokemonEntries.pokemonSpecies.pokemonName
        }
    }

    interface OnListenerPokedex {
        fun onClickPokedex(pokemonEntries: Pokemon_entries)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.pokedex_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemonEntries = pokemonEntries[position]
        holder.bind(pokemonEntries)
        holder.cardView.setOnClickListener {
            onListenerPokedex.onClickPokedex(pokemonEntries)
        }
    }

    override fun getItemCount(): Int = pokemonEntries.size

}