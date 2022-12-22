package s8u.studies.myapplication.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin
import s8u.studies.myapplication.R
import s8u.studies.myapplication.databinding.ActivityHomeBinding
import s8u.studies.myapplication.model.Pokedex.PokedexEntries
import s8u.studies.myapplication.modules.networkModule
import s8u.studies.myapplication.recyclerview.adapter.ListPokedexAdapter
import s8u.studies.myapplication.viewModel.HomeViewModel

class HomeActivity : AppCompatActivity(), ListPokedexAdapter.OnListenerPokedex {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setKoinUp()
        pokedexItemObjects()
        setObservers()
    }

    private fun setKoinUp() {
        startKoin {
            modules(networkModule)
        }
    }

    private fun setObservers() {
        viewModel.setLoadingState(true)
        viewModel.loadingPokeballTrue.observe(this) {
            visibilityLayout(View.VISIBLE, View.INVISIBLE)
        }
        viewModel.loadingPokeballFalse.observe(this) {
            visibilityLayout(View.INVISIBLE, View.VISIBLE)
        }
    }

    private fun visibilityLayout(loading: Int, information: Int) {
        binding.textViewTitleApp.visibility = information
        binding.textViewTitleFilter.visibility = information
        binding.scrollViewFilter.visibility = information
        binding.inputLayout.visibility = information
        binding.titleListPokemon.visibility = information
        binding.RecyclerView.visibility = information
        binding.loading.visibility = loading
    }

    private fun pokedexItemObjects() {
        val recyclerView = findViewById<RecyclerView>(R.id.RecyclerView)
        viewModel.getPokedexEntriesList()

        viewModel.listPokedexEntriesLiveData.observe(this){
            viewModel.getPokedexTypesList()
        }

        viewModel.listPokedexTypesLiveData.observe(this) {
            val listPokedexEntries = viewModel.listPokedexEntriesLiveData.value
            val typeList = viewModel.listPokedexTypesLiveData.value
            recyclerView.adapter = ListPokedexAdapter(
                this, pokedexEntries = listPokedexEntries!!, typeList!!,this
            )
            viewModel.setLoadingState(false)
        }
    }

    override fun onClickPokedex(pokedexEntries: PokedexEntries) {
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