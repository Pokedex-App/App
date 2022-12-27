package s8u.studies.myapplication.model.Pokemon.abilities

import com.google.gson.annotations.SerializedName

data class PokemonMove(
    @SerializedName("name")
    var moveName: String
)