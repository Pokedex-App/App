package s8u.studies.myapplication.model.Pokedex

import com.google.gson.annotations.SerializedName

data class PokedexSpecies(
    @SerializedName("name")
     var pokemonName:String,
    @SerializedName("url")
     var url:String
)