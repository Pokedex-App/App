package s8u.studies.myapplication.model.Pokemon

import com.google.gson.annotations.SerializedName
import s8u.studies.myapplication.model.Pokemon.PokemonTypeList

data class PokemonTypes(
    @SerializedName("type")
    var type: PokemonTypeList
)