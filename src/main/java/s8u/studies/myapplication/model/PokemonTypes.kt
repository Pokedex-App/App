package s8u.studies.myapplication.model

import com.google.gson.annotations.SerializedName

data class PokemonTypes(
    @SerializedName("type")
    var type: PokemonTypeList
)