package s8u.studies.myapplication.model.Pokedex

import com.google.gson.annotations.SerializedName

data class PokemonTypes (
    @SerializedName("name")
    var nome:String,
    @SerializedName("pokemon")
     var pokedexSpecies: ArrayList<PokedexTypes>
)