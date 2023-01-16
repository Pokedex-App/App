package s8u.studies.myapplication.recyclerview.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import s8u.studies.myapplication.R
import s8u.studies.myapplication.model.ColorBackgroundType
import s8u.studies.myapplication.model.Pokedex.PokedexEntries
import s8u.studies.myapplication.model.Pokemon.Pokemon
import s8u.studies.myapplication.model.Pokemon.PokedexTypes

class ListPokedexAdapter(
    private val context: Context,
    private val pokedexEntriesAndType: Pair<ArrayList<PokedexEntries>?, ArrayList<PokedexTypes>?>,
    private val onListenerPokedex: OnListenerPokedex,
) : RecyclerView.Adapter<ListPokedexAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView: ConstraintLayout = itemView.findViewById(R.id.container_view)
        lateinit var api: Pokemon

        fun bind(pokedexEntries: PokedexEntries, typeList: PokedexTypes, context: Context) {
            val id = itemView.findViewById<TextView>(R.id.pokedex_id)
            id.text = "#${pokedexEntries.id}"
            val name = itemView.findViewById<TextView>(R.id.pokedex_name)
            name.text = pokedexEntries.pokedexSpecies.pokemonName
            val primaryType = itemView.findViewById<TextView>(R.id.pokedex_type1)
            primaryType.text = typeList.typeList[0].type.name
            primaryType.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    context,
                    ColorBackgroundType.TYPE.getColor(typeList.typeList[0].type.name)
                )
            )
            val secondaryType = itemView.findViewById<TextView>(R.id.pokedex_type2)

            if (typeList.typeList.size == 2) {
                secondaryType.text = typeList.typeList[1].type.name
                secondaryType.visibility = View.VISIBLE
                secondaryType.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        context,
                        ColorBackgroundType.TYPE.getColor(typeList.typeList[1].type.name)
                    )
                )
            } else {
                secondaryType.visibility = View.INVISIBLE
            }
        }
    }

    interface OnListenerPokedex {
        fun onClickPokedex(pokedexEntries: PokedexEntries,pokedexTypes:PokedexTypes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.pokedex_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokedexEntries = pokedexEntriesAndType.first!![position]
        val pokedexTypes = pokedexEntriesAndType.second!![position]
        holder.bind(pokedexEntries, pokedexTypes, context)
        holder.cardView.setOnClickListener {
            onListenerPokedex.onClickPokedex(pokedexEntries,pokedexTypes)
        }
    }

    override fun getItemCount(): Int = pokedexEntriesAndType.first!!.size

}