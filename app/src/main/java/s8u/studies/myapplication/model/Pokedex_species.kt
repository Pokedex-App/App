package s8u.studies.myapplication.model

import com.google.gson.annotations.SerializedName

data class Pokedex_species(
    @SerializedName("name")
     var pokemonName:String,
    @SerializedName("url")
     var url:String
)