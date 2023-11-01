package s8u.studies.myapplication.model.Pokedex

import com.google.gson.annotations.SerializedName
import s8u.studies.myapplication.model.Pokedex.PokedexSpecies

data class PokedexTypes (
    @SerializedName("pokemon")
     var pokemon: PokedexSpecies
)