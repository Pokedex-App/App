package s8u.studies.myapplication.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import s8u.studies.myapplication.R
import s8u.studies.myapplication.databinding.ActivityHomeBinding
import s8u.studies.myapplication.databinding.ModalErrorBinding
import s8u.studies.myapplication.model.Pokedex.PokedexEntries
import s8u.studies.myapplication.model.Pokemon.PokedexTypes
import s8u.studies.myapplication.recyclerview.adapter.ListPokedexAdapter
import s8u.studies.myapplication.viewModel.HomeViewModel

class HomeActivity : AppCompatActivity(), ListPokedexAdapter.OnListenerPokedex {
    private val toolbar: androidx.appcompat.widget.Toolbar get() = findViewById(R.id.toolbar_home)
    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding: ActivityHomeBinding
    private lateinit var dialog: AlertDialog
    private var inFilter = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.removeFilter.visibility = View.GONE
        pokedexItemObjects(intent.getIntExtra("regionID", 2))
        setObserversLoading()
        setOnClick()
        highLight(null)
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
                        listFilterPokedex.pokedexSpecies[i].pokemon, i
                    )
                )
                if (i == listFilterPokedex.pokedexSpecies.size - 1) {
                    viewModel.setLiveEntries(listFiltered)
                }
            }
        }
        viewModel.listNamePokemons.observe(this) {
            autoCompleteInput()
            getValueEditText()
        }
    }

    private fun visibilityLayout(loading: Int, information: Int) {
        binding.toolbarHome.toolbarNormal.visibility = information
        binding.textViewTitleApp.visibility = information
        binding.textViewTitleFilter.visibility = information
        binding.scrollViewFilter.visibility = information
        binding.inputLayout.visibility = information
        binding.titleListPokemon.visibility = information
        binding.RecyclerView.visibility = information
        viewModel.isFiltered(inFilter) {
            binding.removeFilter.visibility = information
        }
        binding.loading.visibility = loading
    }

    private fun pokedexItemObjects(regionID: Int) {
        val recyclerView = findViewById<RecyclerView>(R.id.RecyclerView)
        viewModel.getPokedexEntriesList(regionID)

        viewModel.listPokedexEntriesLiveData.observe(this) {
            viewModel.getActualEntriesList()
        }
        viewModel.actualListEntriesLiveData.observe(this) {
            viewModel.getPokedexTypesList()
        }

        viewModel.listPokedexTypesLiveData.observe(this) {
            val actualListPokedexEntries = viewModel.actualListEntriesLiveData.value
            val typeList = viewModel.listPokedexTypesLiveData.value
            val entriesAndTypeList = Pair(actualListPokedexEntries, typeList)
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
        binding.inputLayout.setOnKeyListener { _, keyCode, keyEvent ->
            if ((keyCode == KeyEvent.KEYCODE_ENTER) && (keyEvent.action == KeyEvent.ACTION_UP)) {
                val position = getPositionPokemon(binding.inputLayout.text.toString())
                if (position != -1) {
                    onClickPokedex(
                        viewModel.actualListEntriesLiveData.value!![position],
                        viewModel.listPokedexTypesLiveData.value!![position]
                    )
                    return@setOnKeyListener true
                } else {
                    showErrorModal()
                }
            }
            false
        }
    }

    private fun showErrorModal() {
        val build = AlertDialog.Builder(this, R.style.ThemeCustomDialog)
        val dialogBinding: ModalErrorBinding = ModalErrorBinding
            .inflate(LayoutInflater.from(this))

        dialogBinding.textViewTitleError.text = getText(
            R.string.modal_error_title_pokemonNotFound
        )
        dialogBinding.textViewDescriptionError.text = getText(
            R.string.modal_error_description_pokemonNotFound
        )
        dialogBinding.buttonClose.setOnClickListener { dialog.dismiss() }
        build.setView(dialogBinding.root)
        dialog = build.create()
        dialog.show()
    }

    private fun filterByType(id: String) {
        inFilter = 1
        viewModel.getPokedexFilteredList(id)
        viewModel.setLoadingState(true)
        closeKeyBoard()
    }

    private fun setOnClick() {
        binding.bugImg.setOnClickListener { filterByType("7");highLight(binding.bugImg) }
        binding.darkImg.setOnClickListener { filterByType("17");highLight(binding.darkImg) }
        binding.dragonImg.setOnClickListener { filterByType("16");highLight(binding.dragonImg) }
        binding.electricImg.setOnClickListener { filterByType("13");highLight(binding.electricImg) }
        binding.fightingImg.setOnClickListener { filterByType("2");highLight(binding.fightingImg) }
        binding.fairyImg.setOnClickListener { filterByType("18");highLight(binding.fairyImg) }
        binding.fireImg.setOnClickListener { filterByType("10");highLight(binding.fireImg) }
        binding.flyingImg.setOnClickListener { filterByType("3");highLight(binding.flyingImg) }
        binding.ghostImg.setOnClickListener { filterByType("8");highLight(binding.ghostImg) }
        binding.grassImg.setOnClickListener { filterByType("12");highLight(binding.grassImg) }
        binding.groundImg.setOnClickListener { filterByType("5");highLight(binding.groundImg) }
        binding.iceImg.setOnClickListener { filterByType("15");highLight(binding.iceImg) }
        binding.normalImg.setOnClickListener { filterByType("1");highLight(binding.normalImg) }
        binding.poisonImg.setOnClickListener { filterByType("4");highLight(binding.poisonImg) }
        binding.psychicImg.setOnClickListener { filterByType("14");highLight(binding.psychicImg) }
        binding.rockImg.setOnClickListener { filterByType("6");highLight(binding.rockImg) }
        binding.steelImg.setOnClickListener { filterByType("9");highLight(binding.steelImg) }
        binding.waterImg.setOnClickListener { filterByType("11");highLight(binding.waterImg) }
        binding.removeFilter.setOnClickListener {
            inFilter = 0
            binding.removeFilter.visibility = View.INVISIBLE
            highLight(null)
            viewModel.getActualEntriesList()
            viewModel.setLoadingState(true)
        }
        toolbar.setNavigationOnClickListener { onBackPressed();recreate() }
    }

    private fun highLight(image: ImageView?) {
        val listImageView = arrayListOf(
            binding.bugImg,
            binding.darkImg,
            binding.dragonImg,
            binding.electricImg,
            binding.fightingImg,
            binding.fairyImg,
            binding.fireImg,
            binding.flyingImg,
            binding.ghostImg,
            binding.grassImg,
            binding.groundImg,
            binding.iceImg,
            binding.normalImg,
            binding.poisonImg,
            binding.psychicImg,
            binding.rockImg,
            binding.steelImg,
            binding.waterImg
        )
        for (i in 0 until listImageView.size) {
            if (listImageView[i] != image) listImageView[i].setBackgroundResource(0)
            else listImageView[i].setBackgroundResource(R.drawable.highlight)
        }
    }

    override fun onClickPokedex(pokedexEntries: PokedexEntries, pokedexTypes: PokedexTypes) {
        val typeList = viewModel.listPokedexTypesLiveData.value!!
        val intent = Intent(this, DescriptionActivity::class.java)
        intent.putExtra("id", pokedexTypes.id)
        intent.putExtra("firstPokemon", typeList[0].id)
        val b = Bundle()
        b.putSerializable("listOrder", typeList)
        intent.putExtra("listOrder", b)
        intent.putExtra("position", typeList.indexOf(pokedexTypes))
        intent.putExtra("lastPokemon", typeList[typeList.size - 1].id)

        Log.i("INTENT", typeList[0].id)
        Log.i("INTENT", typeList[typeList.size - 1].id)

        closeKeyBoard()
        startActivity(intent)
    }

    private fun getPositionPokemon(namePokemon: String): Int {
        for (i in 0 until viewModel.actualListEntriesLiveData.value!!.size) {
            if (viewModel.actualListEntriesLiveData.value!![i].pokedexSpecies.pokemonName == namePokemon) {
                return i
            }
        }
        return -1
    }

    private fun closeKeyBoard() {
        val view: View? = currentFocus
        view?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}