package s8u.studies.myapplication.model

import com.google.gson.annotations.SerializedName

data class Pokedex_entries (
    @SerializedName("entry_number")
    var id:Int,
    @SerializedName("pokemon_species")
     var pokedexSpecies: Pokedex_species
)