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
    val pokemonLiveData3 = MutableLiveData<Unit>()

    fun getPokemonDescription(id: String,PrimeiroPokemon:String,UltimoPokemon:String){
        val pokemonEndpoint = retrofitObject.createNetworkService<pokemonEndpoint>()
        val pokemonDescEndpoint = retrofitObject.createNetworkService<pokemonDescriptionEndpoint>()

        hideButtons(id,PrimeiroPokemon,UltimoPokemon)
        
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

    fun hideButtons(id: String,PrimeiroPokemon:String,UltimoPokemon:String){

        if(id.toInt() == PrimeiroPokemon.toInt()){
         pokemonLiveData1.postValue(Unit)
        } else if(id.toInt() == UltimoPokemon.toInt()){
            pokemonLiveData2.postValue(Unit)
        }else{
            pokemonLiveData3.postValue(Unit)
        }
    }
}