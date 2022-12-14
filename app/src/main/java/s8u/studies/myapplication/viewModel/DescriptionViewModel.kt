package s8u.studies.myapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import s8u.studies.myapplication.api.pokemonDescriptionEndpoint
import s8u.studies.myapplication.api.pokemonEndpoint
import s8u.studies.myapplication.di.retrofitObject
import s8u.studies.myapplication.model.PokemonData
import s8u.studies.myapplication.model.PokemonTypes

class DescriptionViewModel() : ViewModel() {
    private val _apiData = MutableLiveData<PokemonData>()
    val apiData: LiveData<PokemonData> = _apiData
    val pokemonLiveData1 = MutableLiveData<Unit>()
    val pokemonLiveData2 = MutableLiveData<Unit>()

    fun getPokemonDescription(id: String){
        val pokemonEndpoint = retrofitObject.createNetworkService<pokemonEndpoint>()
        val pokemonDescEndpoint = retrofitObject.createNetworkService<pokemonDescriptionEndpoint>()

        viewModelScope.launch {
            val poke = pokemonEndpoint.getPokemon(id)
            val pokeDesc = pokemonDescEndpoint.getPokemon(id)

            _apiData.postValue(
                PokemonData(
                    poke.id,
                    poke.name,
                    poke.height,
                    poke.weight,
                    poke.typeList,
                    poke.imgList,
                    pokeDesc.pastEvolution,
                    pokeDesc.DescriptionList
                )
            )
        }
    }

    fun changeBasedOnTypes(typeList: ArrayList<PokemonTypes>) {
        when (typeList.size) {
            1 -> pokemonLiveData1.postValue(Unit)
            else -> pokemonLiveData2.postValue(Unit)
        }
    }
}