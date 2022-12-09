package s8u.studies.myapplication.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import s8u.studies.myapplication.R
import s8u.studies.myapplication.model.Pokedex
import s8u.studies.myapplication.recyclerview.adapter.ListPokedexAdapter

class HomeActivity : AppCompatActivity(), ListPokedexAdapter.OnListenerPokedex {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        pokedexItemObjects()
    }

    private fun pokedexItemObjects() {
        val recyclerView = findViewById<RecyclerView>(R.id.RecyclerView)
        recyclerView.adapter = ListPokedexAdapter(
            this, pokedex = listOf(
                Pokedex(
                    id = "001",
                    name = "Bulbasaur"
                ),
                Pokedex(
                    id = "002",
                    name = "Venussaur"
                )
            ), this
        )
    }

    override fun onClickPokedex(pokedex: Pokedex) {
        // No lugar de "pokedex.name" colocar outra intent.
        // Passar a id do Pokemon selecionado através da intent.putExtra().
        val intent = Intent(pokedex.name)
        startActivity(intent)
    }
}