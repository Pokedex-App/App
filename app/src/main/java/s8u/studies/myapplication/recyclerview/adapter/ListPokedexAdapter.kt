package s8u.studies.myapplication.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.runBlocking
import s8u.studies.myapplication.R
import s8u.studies.myapplication.api.PokemonEndpoint
import s8u.studies.myapplication.di.RetrofitObject
import s8u.studies.myapplication.model.Pokedex.PokedexEntries
import s8u.studies.myapplication.model.Pokemon.Pokemon

class ListPokedexAdapter(
    private val context: Context,
    private val pokedexEntries: ArrayList<PokedexEntries>,
    private val onListenerPokedex: OnListenerPokedex,
) : RecyclerView.Adapter<ListPokedexAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView: ConstraintLayout = itemView.findViewById(R.id.container_view)
        lateinit var api: Pokemon
        fun bind(pokedexEntries: PokedexEntries) {
            val id = itemView.findViewById<TextView>(R.id.pokedex_id)
            id.text = "#${pokedexEntries.id}"
            val name = itemView.findViewById<TextView>(R.id.pokedex_name)
            name.text = pokedexEntries.pokedexSpecies.pokemonName
//            runBlocking {
//                api = RetrofitObject.createNetworkService<PokemonEndpoint>().getPokemon(pokedexEntries.id.toString())
//            }
//            val typePrimary = itemView.findViewById<TextView>(R.id.pokedex_type1)
//            typePrimary.text = api.typeList[0].type.name
        }
    }

    interface OnListenerPokedex {
        fun onClickPokedex(pokedexEntries: PokedexEntries)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.pokedex_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokedexEntries = pokedexEntries[position]
        holder.bind(pokedexEntries)
        holder.cardView.setOnClickListener {
            onListenerPokedex.onClickPokedex(pokedexEntries)
        }
    }

    override fun getItemCount(): Int = pokedexEntries.size

}