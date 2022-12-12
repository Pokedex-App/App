package s8u.studies.myapplication.model

import com.google.gson.annotations.SerializedName

data class Pokedex(
    @SerializedName("pokemon_entries")
    var entriesList: ArrayList<Pokedex_entries>
)