package s8u.studies.myapplication.model.Pokedex

import com.google.gson.annotations.SerializedName

data class Pokedex(
    @SerializedName("pokemon_entries")
    var entriesList: ArrayList<PokedexEntries>
)