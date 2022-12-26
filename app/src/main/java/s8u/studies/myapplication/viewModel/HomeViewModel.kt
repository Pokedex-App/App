package s8u.studies.myapplication.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import s8u.studies.myapplication.api.PokedexTypeEndpoint
import s8u.studies.myapplication.api.PokemonTypeEndpoint
import s8u.studies.myapplication.di.RetrofitObject
import s8u.studies.myapplication.model.Pokedex.PokedexEntries
import s8u.studies.myapplication.model.Pokedex.PokedexTypes
import s8u.studies.myapplication.model.Pokedex.PokedexTypesList
import s8u.studies.myapplication.model.Pokemon.PokemonTypeEnd
import s8u.studies.myapplication.repository.PokedexRepository

class HomeViewModel(private val pokedex: PokedexRepository) : ViewModel() {
    private var _listPokedexEntriesLiveData = MutableLiveData<ArrayList<PokedexEntries>>()
    val listPokedexEntriesLiveData: LiveData<ArrayList<PokedexEntries>> = _listPokedexEntriesLiveData

    private var _listPokedexTypesLiveData = MutableLiveData<ArrayList<PokemonTypeEnd>>()
    val listPokedexTypesLiveData: LiveData<ArrayList<PokemonTypeEnd>> = _listPokedexTypesLiveData

    private var _listPokedexFilteredLiveData = MutableLiveData<PokedexTypes>()
    val listPokedexFilteredLiveData: LiveData<PokedexTypes> = _listPokedexFilteredLiveData

    private var _loadingPokeballTrue = MutableLiveData<Unit>()
    val loadingPokeballTrue: LiveData<Unit> = _loadingPokeballTrue

    private var _loadingPokeballFalse = MutableLiveData<Unit>()
    val loadingPokeballFalse: LiveData<Unit> = _loadingPokeballFalse


    fun getPokedexEntriesList() {
        viewModelScope.launch {
            _listPokedexEntriesLiveData.postValue(pokedex.getPokedex().entriesList)
        }
    }

    fun getPokedexFilteredList(id:String){
        val api = RetrofitObject.createNetworkService<PokemonTypeEndpoint>()

        viewModelScope.launch {
            val a = api.getPokemon(id)
            _listPokedexFilteredLiveData.postValue(a)
        }
    }

    fun getPokedexTypesList() {
        val typeList = arrayListOf<PokemonTypeEnd>()
        val api = RetrofitObject.createNetworkService<PokedexTypeEndpoint>()
        viewModelScope.launch {
            for (i in 0 until listPokedexEntriesLiveData.value!!.size) {
                Log.i("LOADING", i.toString())
                solveApiProblems(listPokedexEntriesLiveData.value!![i])

                typeList.add(api.getPokemon(listPokedexEntriesLiveData.value!![i].pokedexSpecies.pokemonName))

                if (i == listPokedexEntriesLiveData.value!!.size - 1) {
                    _listPokedexTypesLiveData.postValue(typeList)
                }
            }
        }
    }

    fun solveApiProblems(entries: PokedexEntries){
        if(entries.pokedexSpecies.pokemonName == "wormadam"){
            entries.pokedexSpecies.pokemonName += "-plant"
        }
        else if(entries.pokedexSpecies.pokemonName == "deoxys"){
            entries.pokedexSpecies.pokemonName += "-normal"
        }
        else if(entries.pokedexSpecies.pokemonName == "giratina"){
            entries.pokedexSpecies.pokemonName += "-altered"
        }
        else if(entries.pokedexSpecies.pokemonName == "basculin"){
            entries.pokedexSpecies.pokemonName += "-red-striped"
        }
        else if(entries.pokedexSpecies.pokemonName == "darmanitan"){
            entries.pokedexSpecies.pokemonName += "-standard"
        }
        else if(entries.pokedexSpecies.pokemonName == "tornadus"){
            entries.pokedexSpecies.pokemonName += "-incarnate"
        }
        else if(entries.pokedexSpecies.pokemonName == "thundurus"){
            entries.pokedexSpecies.pokemonName += "-incarnate"
        }
        else if(entries.pokedexSpecies.pokemonName == "landorus"){
            entries.pokedexSpecies.pokemonName += "-incarnate"
        }
        else if(entries.pokedexSpecies.pokemonName == "keldeo"){
            entries.pokedexSpecies.pokemonName += "-ordinary"
        }
        else if(entries.pokedexSpecies.pokemonName == "meloetta"){
            entries.pokedexSpecies.pokemonName += "-aria"
        }
        else if(entries.pokedexSpecies.pokemonName == "shaymin"){
            entries.pokedexSpecies.pokemonName += "-land"
        }
        else if(entries.pokedexSpecies.pokemonName == "meowstic"){
            entries.pokedexSpecies.pokemonName += "-male"
        }
        else if(entries.pokedexSpecies.pokemonName == "aegislash"){
            entries.pokedexSpecies.pokemonName += "-shield"
        }
        else if(entries.pokedexSpecies.pokemonName == "pumpkaboo"){
            entries.pokedexSpecies.pokemonName += "-average"
        }
        else if(entries.pokedexSpecies.pokemonName == "gourgeist"){
            entries.pokedexSpecies.pokemonName += "-average"
        }
        else if(entries.pokedexSpecies.pokemonName == "zygarde"){
            entries.pokedexSpecies.pokemonName += "-50"
        }
        else if(entries.pokedexSpecies.pokemonName == "oricorio"){
            entries.pokedexSpecies.pokemonName += "-baile"
        }
        else if(entries.pokedexSpecies.pokemonName == "lycanroc"){
            entries.pokedexSpecies.pokemonName += "-midday"
        }
        else if(entries.pokedexSpecies.pokemonName == "wishiwashi"){
            entries.pokedexSpecies.pokemonName += "-solo"
        }
        else if(entries.pokedexSpecies.pokemonName == "minior"){
            entries.pokedexSpecies.pokemonName += "-red-meteor"
        }
        else if(entries.pokedexSpecies.pokemonName == "mimikyu"){
            entries.pokedexSpecies.pokemonName += "-disguised"
        }
        else if(entries.pokedexSpecies.pokemonName == "toxtricity"){
            entries.pokedexSpecies.pokemonName += "-amped"
        }
        else if(entries.pokedexSpecies.pokemonName == "eiscue"){
            entries.pokedexSpecies.pokemonName += "-ice"
        }
        else if(entries.pokedexSpecies.pokemonName == "indeedee"){
            entries.pokedexSpecies.pokemonName += "-male"
        }
        else if(entries.pokedexSpecies.pokemonName == "morpeko"){
            entries.pokedexSpecies.pokemonName += "-full-belly"
        }
        else if(entries.pokedexSpecies.pokemonName == "urshifu"){
            entries.pokedexSpecies.pokemonName += "-single-strike"
        }

    }
    fun setLoadingState(isVisible: Boolean) {
        if (isVisible) _loadingPokeballTrue.postValue(Unit)
        else _loadingPokeballFalse.postValue(Unit)
    }

}