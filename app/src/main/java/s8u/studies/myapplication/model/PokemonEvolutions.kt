package s8u.studies.myapplication.model

import com.google.gson.annotations.SerializedName

data class PokemonEvolutions (
    @SerializedName("name")
    var nomeEvolucao:String
        )