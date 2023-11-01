package s8u.studies.myapplication.viewModel

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import s8u.studies.myapplication.di.Status
import s8u.studies.myapplication.model.Pokedex.PokedexEntries
import s8u.studies.myapplication.model.Pokedex.PokedexSpecies
import s8u.studies.myapplication.model.Pokedex.PokemonTypes
import s8u.studies.myapplication.model.Pokemon.PokedexTypes
import s8u.studies.myapplication.repository.PokedexRepository

class HomeViewModel(private val repository: PokedexRepository) : ViewModel() {
    private var _listPokedexEntriesLiveData = MutableLiveData<ArrayList<PokedexEntries>>()
    val listPokedexEntriesLiveData: LiveData<ArrayList<PokedexEntries>> =
        _listPokedexEntriesLiveData

    private var _actualListEntriesLiveData = MutableLiveData<ArrayList<PokedexEntries>>()
    val actualListEntriesLiveData: LiveData<ArrayList<PokedexEntries>> =
        _actualListEntriesLiveData

    private var _listPokedexTypesLiveData = MutableLiveData<ArrayList<PokedexTypes>>()
    val listPokedexTypesLiveData: LiveData<ArrayList<PokedexTypes>> = _listPokedexTypesLiveData

    private var _listPokedexFilteredLiveData = MutableLiveData<PokemonTypes>()
    val listPokedexFilteredLiveData: LiveData<PokemonTypes> = _listPokedexFilteredLiveData

    private var _loadingPokeballTrue = MutableLiveData<Unit>()
    val loadingPokeballTrue: LiveData<Unit> = _loadingPokeballTrue

    private var _loadingPokeballFalse = MutableLiveData<Unit>()
    val loadingPokeballFalse: LiveData<Unit> = _loadingPokeballFalse

    private var _listNamePokemons = MutableLiveData<ArrayList<String>>()
    val listNamePokemons: LiveData<ArrayList<String>> = _listNamePokemons

    fun getPokedexEntriesList(regionId: Int, context: Context) {
        viewModelScope.launch {
            val response = repository.getPokedex(regionId.toString())
            if (response.status == Status.SUCCESS) {
                _listPokedexEntriesLiveData.postValue(response.data!!.entriesList)
            } else if (response.status == Status.ERROR) {
                val intent = Intent(Intent.ACTION_MAIN)
                if (response.message == "Timeout") {
                    intent.component = ComponentName(
                        "s8u.studies.myapplication",
                        "s8u.studies.myapplication.view.TimeOutActivity"
                    )
                    startActivity(context, intent, null)
                } else if (response.message == "Something went wrong") {
                    intent.component = ComponentName(
                        "s8u.studies.myapplication",
                        "s8u.studies.myapplication.view.ErrorActivity"
                    )
                    startActivity(context, intent, null)
                }

            }
        }
    }

    fun getActualEntriesList() {
        _actualListEntriesLiveData.postValue(listPokedexEntriesLiveData.value)
    }

    fun getPokedexFilteredList(id: String, context: Context) {
        viewModelScope.launch {
            val response = repository.getPokemonType(id)

            if (response.status == Status.SUCCESS) {
                _listPokedexFilteredLiveData.postValue(response.data!!)
            } else if (response.status == Status.ERROR) {
                val intent = Intent(Intent.ACTION_MAIN)
                if (response.message == "Timeout") {
                    intent.component = ComponentName(
                        "s8u.studies.myapplication",
                        "s8u.studies.myapplication.view.TimeOutActivity"
                    )
                    startActivity(context, intent, null)
                } else if (response.message == "Something went wrong") {
                    intent.component = ComponentName(
                        "s8u.studies.myapplication",
                        "s8u.studies.myapplication.view.ErrorActivity"
                    )
                    startActivity(context, intent, null)
                }

            }
        }

    }

    fun updateLiveData(species: PokedexSpecies, id: Int): PokedexEntries {
        return PokedexEntries(id, species)
    }

    fun setLiveEntries(list: ArrayList<PokedexEntries>) {
        _actualListEntriesLiveData.value = list
    }

    fun getPokedexTypesList(context: Context) {
        val typeList = arrayListOf<PokedexTypes>()
        val nameList = arrayListOf<String>()
        viewModelScope.launch {
            for (i in 0 until actualListEntriesLiveData.value!!.size) {
                val pokemon = actualListEntriesLiveData.value!![i]
                solveApiProblems(pokemon)
                nameList.add(pokemon.pokedexSpecies.pokemonName)

                val response = repository.getPokedexType(pokemon.pokedexSpecies.pokemonName)

                if (response.status == Status.SUCCESS) {
                    typeList.add(response.data!!)
                } else if (response.status == Status.ERROR) {
                    val intent = Intent(Intent.ACTION_MAIN)
                    if (response.message == "Timeout") {
                        intent.component = ComponentName(
                            "s8u.studies.myapplication",
                            "s8u.studies.myapplication.view.TimeOutActivity"
                        )
                        startActivity(context, intent, null)
                    } else if (response.message == "Something went wrong") {
                        intent.component = ComponentName(
                            "s8u.studies.myapplication",
                            "s8u.studies.myapplication.view.ErrorActivity"
                        )
                        startActivity(context, intent, null)
                    }
                }
                if (i == actualListEntriesLiveData.value!!.size - 1) {
                    _listPokedexTypesLiveData.postValue(typeList)
                    _listNamePokemons.postValue(nameList)
                }
                Log.i("LOADING", i.toString())
            }
        }
    }

    fun solveApiProblems(entries: PokedexEntries) {
        when (entries.pokedexSpecies.pokemonName) {
            "tornadus", "thundurus", "landorus" -> entries.pokedexSpecies.pokemonName += "-incarnate"
            "meowstic", "indeedee" -> entries.pokedexSpecies.pokemonName += "-male"
            "pumpkaboo", "gourgeist" -> entries.pokedexSpecies.pokemonName += "-average"
            "wormadam" -> entries.pokedexSpecies.pokemonName += "-plant"
            "deoxys" -> entries.pokedexSpecies.pokemonName += "-normal"
            "giratina" -> entries.pokedexSpecies.pokemonName += "-altered"
            "basculin" -> entries.pokedexSpecies.pokemonName += "-red-striped"
            "darmanitan" -> entries.pokedexSpecies.pokemonName += "-standard"
            "keldeo" -> entries.pokedexSpecies.pokemonName += "-ordinary"
            "meloetta" -> entries.pokedexSpecies.pokemonName += "-aria"
            "shaymin" -> entries.pokedexSpecies.pokemonName += "-land"
            "aegislash" -> entries.pokedexSpecies.pokemonName += "-shield"
            "zygarde" -> entries.pokedexSpecies.pokemonName += "-50"
            "oricorio" -> entries.pokedexSpecies.pokemonName += "-baile"
            "lycanroc" -> entries.pokedexSpecies.pokemonName += "-midday"
            "wishiwashi" -> entries.pokedexSpecies.pokemonName += "-solo"
            "minior" -> entries.pokedexSpecies.pokemonName += "-red-meteor"
            "mimikyu" -> entries.pokedexSpecies.pokemonName += "-disguised"
            "toxtricity" -> entries.pokedexSpecies.pokemonName += "-amped"
            "eiscue" -> entries.pokedexSpecies.pokemonName += "-ice"
            "morpeko" -> entries.pokedexSpecies.pokemonName += "-full-belly"
            "urshifu" -> entries.pokedexSpecies.pokemonName += "-single-strike"
        }
    }

    fun isFiltered(counter: Int, behavior: () -> Unit) {
        if (counter > 0) behavior()
    }

    fun setLoadingState(isVisible: Boolean) {
        if (isVisible) _loadingPokeballTrue.postValue(Unit)
        else _loadingPokeballFalse.postValue(Unit)
    }
}