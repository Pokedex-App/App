package s8u.studies.myapplication.model

import com.google.gson.annotations.SerializedName

data class Pokemon_entries (
    @SerializedName("entry_number")
    var id:Int,
    @SerializedName("pokemon_species")
     var pokemonSpecies: Pokemon_species
)