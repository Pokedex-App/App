package s8u.studies.myapplication.model.Pokemon.abilities

import com.google.gson.annotations.SerializedName

data class PokemonMoves(
    @SerializedName("move")
    var move: PokemonMove
)