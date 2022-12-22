package s8u.studies.myapplication.viewModel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import s8u.studies.myapplication.R
import s8u.studies.myapplication.api.PokemonDescriptionEndpoint
import s8u.studies.myapplication.api.PokemonEndpoint
import s8u.studies.myapplication.di.RetrofitObject
import s8u.studies.myapplication.model.PokemonData

class DescriptionViewModel : ViewModel() {
    private val _apiData = MutableLiveData<PokemonData>()
    val apiData: LiveData<PokemonData> = _apiData
    val pokemonLiveData1 = MutableLiveData<Unit>()
    val pokemonLiveData3 = MutableLiveData<Unit>()
    val pokemonLiveData2 = MutableLiveData<Unit>()

    fun getPokemonDescription(id: String,firstPokemon:String,lastPokemon:String){
        val pokemonEndpoint = RetrofitObject.createNetworkService<PokemonEndpoint>()
        val pokemonDescEndpoint = RetrofitObject.createNetworkService<PokemonDescriptionEndpoint>()

        hideButtons(id,firstPokemon,lastPokemon)
        
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
                    poke.movesList,
                    pokeDesc.pastEvolution,
                    pokeDesc.DescriptionList
                )
            )
        }
    }

    private fun hideButtons(id: String, firstPokemon:String, lastPokemon:String){
        when (id.toInt()) {
            firstPokemon.toInt() -> pokemonLiveData1.postValue(Unit)
            lastPokemon.toInt() -> pokemonLiveData2.postValue(Unit)
            else -> pokemonLiveData3.postValue(Unit)
        }
    }

    fun visibilitySecondaryType(size: Int) : Int {
        return when (size) {
            1 -> View.GONE
            else -> View.VISIBLE
        }
    }

    fun existsSecondaryType(size: Int, behavior: () -> Unit)  {
        if (size == 2) behavior()
    }

    fun colorBackgroundType(nameType: String) : Int {
        return when (nameType) {
            "bug" -> R.color.bug
            "dark" -> R.color.dark
            "dragon" -> R.color.dragon
            "electric" -> R.color.electric
            "fairy" -> R.color.fairy
            "fighting" -> R.color.fighting
            "fire" -> R.color.fire
            "flying" -> R.color.flying
            "ghost" -> R.color.ghost
            "grass" -> R.color.grass
            "ground" -> R.color.ground
            "ice" -> R.color.ice
            "normal" -> R.color.normal
            "poison" -> R.color.poison
            "psychic" -> R.color.psychic
            "rock" -> R.color.rock
            "steel" -> R.color.steel
            "water" -> R.color.water
            else -> R.color.light_gray_2
        }
    }
}