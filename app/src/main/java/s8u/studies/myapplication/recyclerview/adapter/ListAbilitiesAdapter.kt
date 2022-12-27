package s8u.studies.myapplication.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import s8u.studies.myapplication.R
import s8u.studies.myapplication.model.Pokemon.abilities.PokemonMoves

class ListAbilitiesAdapter(
    private val context: Context,
    private val abilities: ArrayList<PokemonMoves>,
    private val onListenerAbility: OnListenerAbility
) : RecyclerView.Adapter<ListAbilitiesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView: ConstraintLayout = itemView.findViewById(R.id.container_view_abilities)
        fun bind(ability: PokemonMoves) {
            val nameAbility = itemView.findViewById<TextView>(R.id.textView_name_ability)
            nameAbility.text = ability.move.moveName
        }
    }

    interface OnListenerAbility {
        fun onClickAbility(ability: PokemonMoves)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.ability_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ability = abilities[position]
        holder.bind(ability)
        holder.cardView.setOnClickListener {
            onListenerAbility.onClickAbility(ability)
        }
    }

    override fun getItemCount(): Int = abilities.size

}