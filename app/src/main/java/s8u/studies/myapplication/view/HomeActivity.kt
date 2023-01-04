package s8u.studies.myapplication.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin
import s8u.studies.myapplication.R
import s8u.studies.myapplication.databinding.ActivityHomeBinding
import s8u.studies.myapplication.model.Pokedex.PokedexEntries
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
        setOnClick()
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
        viewModel.listPokedexFilteredLiveData.observe(this) {
            val listFiltered = arrayListOf<PokedexEntries>()
            val listFilterPokedex = viewModel.listPokedexFilteredLiveData.value

            for (i in 0 until listFilterPokedex!!.pokedexSpecies.size) {
                listFiltered.add(
                    viewModel.updateLiveData(
                        listFilterPokedex.pokedexSpecies[i].pokemon,
                        i
                    )
                )
                if (i == listFilterPokedex.pokedexSpecies.size - 1) {
                    viewModel.setLiveEntries(listFiltered)
                    viewModel.getPokedexTypesList()
                }
            }
        }
        viewModel.listPokedexTypesLiveData.observe(this) {
            val recyclerView = findViewById<RecyclerView>(R.id.RecyclerView)
            recyclerView.adapter!!.notifyDataSetChanged()
        }
        viewModel.listNamePokemons.observe(this) {
            autoCompleteInput()
            getValueEditText()
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
            val entriesAndTypeList = Pair(listPokedexEntries, typeList)
            recyclerView.adapter = ListPokedexAdapter(
                this, entriesAndTypeList, this
            )
            viewModel.setLoadingState(false)
        }
    }

    private fun autoCompleteInput() {
        val adapter: ArrayAdapter<String> =
            ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                viewModel.listNamePokemons.value!!
            )
        binding.inputLayout.setAdapter(adapter)
    }

    private fun getValueEditText() {
        binding.inputLayout.setOnKeyListener { view, keyCode, keyEvent ->
            if ((keyCode == KeyEvent.KEYCODE_ENTER) && (keyEvent.action == KeyEvent.ACTION_UP)) {
                val position = getPositionPokemon(binding.inputLayout.text.toString())
                if (position != -1) {
                    onClickPokedex(
                        viewModel.listPokedexEntriesLiveData.value!![position],
                        viewModel.listPokedexTypesLiveData.value!![position]
                    )
                    return@setOnKeyListener true
                } else {
                    println("This Pokémon doesn't exist")
                }
            }
            false
        }
    }

    private fun filterByType(id: String) {
        viewModel.getPokedexFilteredList(id)
        viewModel.setLoadingState(true)
    }

    private fun setOnClick() {
        binding.bugImg.setOnClickListener { filterByType("7") }
        binding.darkImg.setOnClickListener { filterByType("17") }
        binding.dragonImg.setOnClickListener { filterByType("16") }
        binding.electricImg.setOnClickListener { filterByType("13") }
        binding.fightingImg.setOnClickListener { filterByType("2") }
        binding.fairyImg.setOnClickListener { filterByType("18") }
        binding.fireImg.setOnClickListener { filterByType("10") }
        binding.flyingImg.setOnClickListener { filterByType("3") }
        binding.ghostImg.setOnClickListener { filterByType("8") }
        binding.grassImg.setOnClickListener { filterByType("12") }
        binding.groundImg.setOnClickListener { filterByType("5") }
        binding.iceImg.setOnClickListener { filterByType("15") }
        binding.normalImg.setOnClickListener { filterByType("1") }
        binding.poisonImg.setOnClickListener { filterByType("4") }
        binding.psychicImg.setOnClickListener { filterByType("14") }
        binding.rockImg.setOnClickListener { filterByType("6") }
        binding.steelImg.setOnClickListener { filterByType("9") }
        binding.waterImg.setOnClickListener { filterByType("11") }
    }

    override fun onClickPokedex(pokedexEntries: PokedexEntries, pokedexTypes: PokemonTypeEnd) {
        val typeList = viewModel.listPokedexTypesLiveData.value!!

        val intent = Intent(this, DescriptionActivity::class.java)
        intent.putExtra("id", pokedexTypes.id)
        intent.putExtra("firstPokemon", typeList[0].id)
        Log.i("INTENT", typeList[0].id)
        val b = Bundle()
        b.putSerializable("listOrder", typeList)
        intent.putExtra("listOrder", b)
        intent.putExtra("position", typeList.indexOf(pokedexTypes))
        intent.putExtra("lastPokemon", typeList[typeList.size - 1].id)
        Log.i("INTENT", typeList[typeList.size - 1].id)

        startActivity(intent)
    }

    private fun getPositionPokemon(namePokemon: String): Int {
        for (i in 0 until viewModel.listPokedexEntriesLiveData.value!!.size) {
            if (viewModel.listPokedexEntriesLiveData.value!![i].pokedexSpecies.pokemonName == namePokemon) {
                return i
            }
        }
        return -1
    }
}