package s8u.studies.myapplication.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import s8u.studies.myapplication.R
import s8u.studies.myapplication.model.Pokedex

class ListPokedexAdapter(
    private val context: Context,
    private val pokedex: List<Pokedex>,
    private val onListenerPokedex: OnListenerPokedex
) : RecyclerView.Adapter<ListPokedexAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView: ConstraintLayout = itemView.findViewById(R.id.container_view)
        fun bind(pokedex: Pokedex) {
            val id = itemView.findViewById<TextView>(R.id.pokedex_id)
            id.text = pokedex.id
            val name = itemView.findViewById<TextView>(R.id.pokedex_name)
//            name.text = pokedex.name
        }
    }

    interface OnListenerPokedex {
        fun onClickPokedex(pokedex: Pokedex)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.pokedex_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokedex = pokedex[position]
        holder.bind(pokedex)
        holder.cardView.setOnClickListener {
            onListenerPokedex.onClickPokedex(pokedex)
        }
    }

    override fun getItemCount(): Int = pokedex.size

}