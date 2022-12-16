package s8u.studies.myapplication.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin
import s8u.studies.myapplication.R
import s8u.studies.myapplication.model.Pokedex_entries
import s8u.studies.myapplication.modules.networkModule
import s8u.studies.myapplication.recyclerview.adapter.ListPokedexAdapter
import s8u.studies.myapplication.viewModel.HomeViewModel

class HomeActivity : AppCompatActivity(), ListPokedexAdapter.OnListenerPokedex {
    private val viewModel: HomeViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setKoinUp()
        pokedexItemObjects()
    }

    private fun setKoinUp() {
        startKoin {
            modules(networkModule)
        }
    }

    private fun pokedexItemObjects() {
        val recyclerView = findViewById<RecyclerView>(R.id.RecyclerView)
        viewModel.getPokedexEntriesList()
        viewModel.listPokedexEntriesLiveData.observe(this) {
            val listPokedexEntries = viewModel.listPokedexEntriesLiveData.value
            recyclerView.adapter = ListPokedexAdapter(
                this, pokedexEntries = listPokedexEntries!!, this
            )
        }
    }

    override fun onClickPokedex(pokedexEntries: Pokedex_entries) {
        val listPokedex = viewModel.listPokedexEntriesLiveData.value!!

        val intent = Intent(this, DescriptionActivity::class.java)
        intent.putExtra("id", pokedexEntries.id.toString())
        intent.putExtra("a",listPokedex[0].id.toString())
        Log.i("INTENT","${listPokedex[0].id}")
        intent.putExtra("b",listPokedex[listPokedex.size - 1].id.toString())
        Log.i("INTENT","${listPokedex[listPokedex.size - 1].id}")
        
        startActivity(intent)
    }
}