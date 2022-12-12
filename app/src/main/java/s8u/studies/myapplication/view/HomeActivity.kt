package s8u.studies.myapplication.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.runBlocking
import s8u.studies.myapplication.R
import s8u.studies.myapplication.api.pokedexEndpoint
import s8u.studies.myapplication.di.retrofitObject
import s8u.studies.myapplication.model.Pokedex
import s8u.studies.myapplication.model.Pokemon_entries
import s8u.studies.myapplication.recyclerview.adapter.ListPokedexAdapter
import java.util.ArrayList

class HomeActivity : AppCompatActivity(), ListPokedexAdapter.OnListenerPokedex {
    private lateinit var listPokedex: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        pokedexItemObjects()
    }

    private fun pokedexItemObjects() {
        val possibleResponse = retrofitObject.createNetworkService<pokedexEndpoint>()
        var listPokemonEntries: ArrayList<Pokemon_entries>
        runBlocking {
            val pokemon = possibleResponse.getPokedex()
            listPokemonEntries= pokemon.entriesList
        }
        val recyclerView = findViewById<RecyclerView>(R.id.RecyclerView)
        recyclerView.adapter = ListPokedexAdapter(
            this, pokemonEntries = listPokemonEntries, this
        )
    }

    override fun onClickPokedex(pokemonEntries: Pokemon_entries) {
        val intent = Intent()
        startActivity(intent)
    }
}