package s8u.studies.myapplication.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import s8u.studies.myapplication.model.Pokedex_entries
import s8u.studies.myapplication.repository.pokedexRepository

class HomeViewModel(private val pokedex : pokedexRepository) : ViewModel() {
    private var _listPokedexEntriesLiveData = MutableLiveData<ArrayList<Pokedex_entries>>()
    val listPokedexEntriesLiveData: LiveData<ArrayList<Pokedex_entries>> = _listPokedexEntriesLiveData

    fun getPokedexEntriesList () {
        Log.i("Retrofit", "Entrei no método.")
        viewModelScope.launch {
            _listPokedexEntriesLiveData.postValue(pokedex.getPokedex().entriesList)
            Log.i("Retrofit", "Foi chamado")
        }
    }
}