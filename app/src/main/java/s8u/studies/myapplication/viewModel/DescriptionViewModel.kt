package s8u.studies.myapplication.viewModel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import s8u.studies.myapplication.model.Pokemon.abilities.PokemonAbilityInformation
import s8u.studies.myapplication.model.PokemonData
import s8u.studies.myapplication.repository.DescriptionRepository

class DescriptionViewModel (private val repository: DescriptionRepository) : ViewModel() {
    private val _apiData = MutableLiveData<PokemonData>()
    val apiData: LiveData<PokemonData> = _apiData
    val pokemonLiveData1 = MutableLiveData<Unit>()
    val pokemonLiveData3 = MutableLiveData<Unit>()
    val pokemonLiveData2 = MutableLiveData<Unit>()

    private var _loadingPokeballTrue = MutableLiveData<Unit>()
    val loadingPokeballTrue: LiveData<Unit> = _loadingPokeballTrue

    private var _loadingPokeballFalse = MutableLiveData<Unit>()
    val loadingPokeballFalse: LiveData<Unit> = _loadingPokeballFalse

    private var _abilityInformationPokemon = MutableLiveData<PokemonAbilityInformation>()
    val abilityInformationPokemon: LiveData<PokemonAbilityInformation> = _abilityInformationPokemon

    fun getPokemonDescription(id: String, firstPokemon: String, lastPokemon: String) {
        hideButtons(id, firstPokemon, lastPokemon)
        viewModelScope.launch {
            val poke = repository.getPokemon(id)
            val pokeDesc = repository.getPokemonDesc(id)
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

    fun getAbilityInformation(nameAbility: String) {
        viewModelScope.launch {
            val apiAbility = repository.getPokemonAbility(nameAbility)
            _abilityInformationPokemon.postValue(
                PokemonAbilityInformation(
                    apiAbility.flavorTextEntries,
                    apiAbility.power,
                    apiAbility.type,
                    apiAbility.damage
                )
            )
        }
    }

    private fun hideButtons(id: String, firstPokemon: String, lastPokemon: String) {
        when (id) {
            firstPokemon -> pokemonLiveData1.postValue(Unit)
            lastPokemon -> pokemonLiveData2.postValue(Unit)
            else -> pokemonLiveData3.postValue(Unit)
        }
    }

    fun visibilitySecondaryType(size: Int): Int {
        return when (size) {
            1 -> View.GONE
            else -> View.VISIBLE
        }
    }

    fun existsSecondaryType(size: Int, behavior: () -> Unit) {
        if (size == 2) behavior()
    }

    fun setLoadingState(isVisible: Boolean) {
        if (isVisible) _loadingPokeballTrue.postValue(Unit)
        else _loadingPokeballFalse.postValue(Unit)
    }
}