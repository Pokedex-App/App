package s8u.studies.myapplication.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.runBlocking
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin
import s8u.studies.myapplication.R
import s8u.studies.myapplication.api.PokedexTypeEndpoint
import s8u.studies.myapplication.api.PokemonTypeEndpoint
import s8u.studies.myapplication.databinding.ActivityHomeBinding
import s8u.studies.myapplication.di.RetrofitObject
import s8u.studies.myapplication.model.Pokedex.PokedexEntries
import s8u.studies.myapplication.model.Pokedex.PokedexSpecies
import s8u.studies.myapplication.model.Pokedex.PokedexTypes
import s8u.studies.myapplication.model.Pokedex.PokedexTypesList
import s8u.studies.myapplication.model.Pokemon.PokemonTypeEnd
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
        setObserversLoading()
        listener()
    }

    private fun setKoinUp() {
        startKoin {
            modules(networkModule)
        }
    }

    private fun setObserversLoading() {
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

        viewModel.listPokedexEntriesLiveData.observe(this) {
            viewModel.getPokedexTypesList()
        }

        viewModel.listPokedexTypesLiveData.observe(this) {
            val listPokedexEntries = viewModel.listPokedexEntriesLiveData.value
            val typeList = viewModel.listPokedexTypesLiveData.value
            val EntriesAndTypeList = Pair(listPokedexEntries,typeList)
            recyclerView.adapter = ListPokedexAdapter(
                this,EntriesAndTypeList!! , this
            )
            viewModel.setLoadingState(false)
        }
    }

//    private fun teste() {
//        val recyclerView = findViewById<RecyclerView>(R.id.RecyclerView)
//
//        val listaFiltrada = arrayListOf<PokedexEntries>()
//
//
//        val ListaPokedex = viewModel.listPokedexEntriesLiveData.value
//        viewModel.getPokedexFilteredList("7")
//
//        viewModel.listPokedexFilteredLiveData.observe(this) {
//            val listaFiltroPokedex = viewModel.listPokedexFilteredLiveData.value
//
//            for (i in 0 until viewModel.listPokedexEntriesLiveData.value!!.size) {
//
//                for (j in 0 until viewModel.listPokedexFilteredLiveData.value!!.pokedexSpecies.size) {
//
//                    if (ListaPokedex!![i].pokedexSpecies.pokemonName == listaFiltroPokedex!!.pokedexSpecies[j].pokemon.pokemonName) {
//                        listaFiltrada.add(ListaPokedex!![i])
//                    }
//
//                    if (listaFiltroPokedex.pokedexSpecies.size - 1 == j) {
//                        viewModel.getTList(listaFiltrada)
//                    }
//                }
//            }
//        }
//        viewModel.listPokedexEntriesLiveData.observe(this) {
//            viewModel.getPokedexTypesList()
//        }
//
//        viewModel.listPokedexTypesLiveData.observe(this) {
//            recyclerView.adapter!!.notifyDataSetChanged()
//        }
//    }

    private fun listener(){
        val bug = findViewById<ImageView>(R.id.bugImg)

        bug.setOnClickListener{
            //teste()
            Toast.makeText(this,"Bug",10)
        }
    }

    override fun onClickPokedex(pokedexEntries: PokedexEntries,pokedexTypes:PokemonTypeEnd) {
        val listPokedex = viewModel.listPokedexEntriesLiveData.value!!
        val typeList = viewModel.listPokedexTypesLiveData.value!!

        val intent = Intent(this, DescriptionActivity::class.java)
        intent.putExtra("id", pokedexTypes.id)
        intent.putExtra("a", typeList[0].id)
        Log.i("INTENT", "${typeList[0].id}")
        intent.putExtra("b", typeList[typeList.size - 1].id)
        Log.i("INTENT", "${typeList[typeList.size - 1].id}")

        startActivity(intent)
    }
}