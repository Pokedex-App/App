package s8u.studies.myapplication.model.Pokedex

import com.google.gson.annotations.SerializedName
import s8u.studies.myapplication.model.Pokedex.PokedexSpecies
import java.io.Serializable

data class PokedexEntries (
    @SerializedName("entry_number")
    var id:Int,
    @SerializedName("pokemon_species")
     var pokedexSpecies: PokedexSpecies
): Serializable