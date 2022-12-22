package s8u.studies.myapplication.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import s8u.studies.myapplication.R
import s8u.studies.myapplication.model.Pokedex.PokedexEntries
import s8u.studies.myapplication.model.Pokemon.Pokemon
import s8u.studies.myapplication.model.Pokemon.PokemonTypeEnd

class ListPokedexAdapter(
    private val context: Context,
    private val pokedexEntries: ArrayList<PokedexEntries>,
    private val typeList: ArrayList<PokemonTypeEnd>,
    private val onListenerPokedex: OnListenerPokedex,
) : RecyclerView.Adapter<ListPokedexAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView: ConstraintLayout = itemView.findViewById(R.id.container_view)
        lateinit var api: Pokemon
        fun bind(pokedexEntries: PokedexEntries, typeList: PokemonTypeEnd) {
            val id = itemView.findViewById<TextView>(R.id.pokedex_id)
            id.text = "#${pokedexEntries.id}"
            val name = itemView.findViewById<TextView>(R.id.pokedex_name)
            name.text = pokedexEntries.pokedexSpecies.pokemonName
            val primaryType = itemView.findViewById<TextView>(R.id.pokedex_type1)
            primaryType.text = typeList.typeList[0].type.name
            val secondaryType = itemView.findViewById<TextView>(R.id.pokedex_type2)

            if (typeList.typeList.size > 1) {
                secondaryType.text = typeList.typeList[1].type.name
            } else {
                secondaryType.visibility = View.GONE
            }
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
        val typeList = typeList[position]
        holder.bind(pokedexEntries, typeList)
        holder.cardView.setOnClickListener {
            onListenerPokedex.onClickPokedex(pokedexEntries)
        }
    }

    override fun getItemCount(): Int = pokedexEntries.size

}