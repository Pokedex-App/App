package s8u.studies.myapplication.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import s8u.studies.myapplication.api.PokemonTypeEndpoint
import s8u.studies.myapplication.di.RetrofitObject
import s8u.studies.myapplication.model.Pokedex.PokedexEntries
import s8u.studies.myapplication.model.Pokemon.PokemonTypeEnd
import s8u.studies.myapplication.repository.PokedexRepository

class HomeViewModel(private val pokedex : PokedexRepository) : ViewModel() {
    private var _listPokedexEntriesLiveData = MutableLiveData<ArrayList<PokedexEntries>>()
    val listPokedexEntriesLiveData: LiveData<ArrayList<PokedexEntries>> = _listPokedexEntriesLiveData

    private var _listPokedexTypesLiveData = MutableLiveData<ArrayList<PokemonTypeEnd>>()
    val listPokedexTypesLiveData: LiveData<ArrayList<PokemonTypeEnd>> = _listPokedexTypesLiveData


    fun getPokedexEntriesList () {
        Log.i("Retrofit", "Entrei no método.")
        viewModelScope.launch {
            _listPokedexEntriesLiveData.postValue(pokedex.getPokedex().entriesList)
            Log.i("Retrofit", "Foi chamado")
        }
    }
    fun getPokedexTypesList () {
        var typeList = arrayListOf<PokemonTypeEnd>()
        val api = RetrofitObject.createNetworkService<PokemonTypeEndpoint>()
        Log.i("Retrofit", "Entrei no método.")
        viewModelScope.launch {
                for(i in 0..listPokedexEntriesLiveData.value!!.size - 1){
                    typeList.add(api.getPokemon(listPokedexEntriesLiveData.value!![i].id.toString()))

                    if(i == listPokedexEntriesLiveData.value!!.size - 1){
                        _listPokedexTypesLiveData.postValue(typeList)
                    }
                }


        }

    }

}