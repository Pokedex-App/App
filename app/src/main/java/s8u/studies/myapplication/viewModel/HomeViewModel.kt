package s8u.studies.myapplication.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import s8u.studies.myapplication.model.Pokedex.PokedexEntries
import s8u.studies.myapplication.repository.PokedexRepository

class HomeViewModel(private val pokedex : PokedexRepository) : ViewModel() {
    private var _listPokedexEntriesLiveData = MutableLiveData<ArrayList<PokedexEntries>>()
    val listPokedexEntriesLiveData: LiveData<ArrayList<PokedexEntries>> = _listPokedexEntriesLiveData

    fun getPokedexEntriesList () {
        Log.i("Retrofit", "Entrei no método.")
        viewModelScope.launch {
            _listPokedexEntriesLiveData.postValue(pokedex.getPokedex().entriesList)
            Log.i("Retrofit", "Foi chamado")
        }
    }
}