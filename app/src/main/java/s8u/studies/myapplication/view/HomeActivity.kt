package s8u.studies.myapplication.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.runBlocking
import s8u.studies.myapplication.R
import s8u.studies.myapplication.api.pokedexEndpoint
import s8u.studies.myapplication.di.retrofitObject
import s8u.studies.myapplication.model.Pokedex_entries
import s8u.studies.myapplication.recyclerview.adapter.ListPokedexAdapter
import java.util.ArrayList

class HomeActivity : AppCompatActivity(), ListPokedexAdapter.OnListenerPokedex {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        pokedexItemObjects()
    }

    private fun pokedexItemObjects() {
        Log.i("Retrofit", "Entrei no método")
        val possibleResponse = retrofitObject.createNetworkService<pokedexEndpoint>()
        var listPokedexEntries: ArrayList<Pokedex_entries>
        runBlocking {
            val pokemon = possibleResponse.getPokedex()
            listPokedexEntries= pokemon.entriesList
            Log.i("Retrofit", "Foi chamado")
        }
        val recyclerView = findViewById<RecyclerView>(R.id.RecyclerView)
        recyclerView.adapter = ListPokedexAdapter(
            this, pokedexEntries = listPokedexEntries, this
        )
    }

    override fun onClickPokedex(pokedexEntries: Pokedex_entries) {
        // Colocar a intent da tela de descrição pokemon
        // Passar em um intentExtra o id do pokemon clicado
        val intent = Intent()
        startActivity(intent)
    }
}