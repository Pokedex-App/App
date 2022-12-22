package s8u.studies.myapplication.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import s8u.studies.myapplication.api.PokemonTypeEndpoint
import s8u.studies.myapplication.di.RetrofitObject
import s8u.studies.myapplication.model.Pokedex.PokedexEntries
import s8u.studies.myapplication.model.Pokemon.PokemonTypeEnd
import s8u.studies.myapplication.repository.PokedexRepository

class HomeViewModel(private val pokedex: PokedexRepository) : ViewModel() {
    private var _listPokedexEntriesLiveData = MutableLiveData<ArrayList<PokedexEntries>>()
    val listPokedexEntriesLiveData: LiveData<ArrayList<PokedexEntries>> =
        _listPokedexEntriesLiveData

    private var _listPokedexTypesLiveData = MutableLiveData<ArrayList<PokemonTypeEnd>>()
    val listPokedexTypesLiveData: LiveData<ArrayList<PokemonTypeEnd>> = _listPokedexTypesLiveData

    private var _loadingPokeballTrue = MutableLiveData<Unit>()
    val loadingPokeballTrue: LiveData<Unit> = _loadingPokeballTrue

    private var _loadingPokeballFalse = MutableLiveData<Unit>()
    val loadingPokeballFalse: LiveData<Unit> = _loadingPokeballFalse


    fun getPokedexEntriesList() {
        viewModelScope.launch {
            _listPokedexEntriesLiveData.postValue(pokedex.getPokedex().entriesList)
        }
    }

    fun getPokedexTypesList() {
        val typeList = arrayListOf<PokemonTypeEnd>()
        val api = RetrofitObject.createNetworkService<PokemonTypeEndpoint>()
        viewModelScope.launch {
            for (i in 0 until listPokedexEntriesLiveData.value!!.size) {
                Log.i("DST", i.toString())
                typeList.add(api.getPokemon(listPokedexEntriesLiveData.value!![i].id.toString()))

                if (i == listPokedexEntriesLiveData.value!!.size - 1) {
                    _listPokedexTypesLiveData.postValue(typeList)
                }
            }
        }
    }

    fun setLoadingState(isVisible: Boolean) {
        if (isVisible) _loadingPokeballTrue.postValue(Unit)
        else _loadingPokeballFalse.postValue(Unit)
    }

}