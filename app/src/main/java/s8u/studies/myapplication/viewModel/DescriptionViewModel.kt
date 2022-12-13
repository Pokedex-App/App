package s8u.studies.myapplication.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import s8u.studies.myapplication.api.pokemonDescriptionEndpoint
import s8u.studies.myapplication.api.pokemonEndpoint
import s8u.studies.myapplication.di.retrofitObject
import s8u.studies.myapplication.model.PokemonData

class DescriptionViewModel() : ViewModel() {

    private val _apiData = MutableLiveData<PokemonData>()
    val apiData: LiveData<PokemonData> = _apiData

    private val _apiData2 = MutableLiveData<String>()
    val apiData2: LiveData<String> = _apiData2

    fun GetPokemonsDescription(id: String){

        val pokemonEndpoint = retrofitObject.createNetworkService<pokemonEndpoint>()
        val pokemonDescEndpoint = retrofitObject.createNetworkService<pokemonDescriptionEndpoint>()

        viewModelScope.launch {
            val poke = pokemonEndpoint.getPokemon(id)
            val pokeDesc = pokemonDescEndpoint.getPokemon(id)

            _apiData.postValue(PokemonData(poke.id,
                poke.name,
                poke.height,
                poke.weight,
                poke.typeList,
                poke.imgList,
                pokeDesc.pastEvolution,
                pokeDesc.DescriptionList))
            _apiData2.postValue(poke.name)
            Log.i("Retrofit", "teste ${poke.name}")
        }
    }


}