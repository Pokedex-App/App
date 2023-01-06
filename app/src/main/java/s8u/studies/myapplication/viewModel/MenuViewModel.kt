package s8u.studies.myapplication.viewModel

import androidx.lifecycle.ViewModel

class MenuViewModel:ViewModel() {
    fun getRegionsId(region:String): Int {
        return when (region) {
            "Kanto" -> 2
            "Johto" -> 3
            "Hoenn" -> 4
            "Sinnoh" -> 5
            "Unova" -> 8
            "Kalos" -> 12
            "Alola" -> 16
            else -> -1
        }
    }

}