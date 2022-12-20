package s8u.studies.myapplication.model.Pokemon.Description

import com.google.gson.annotations.SerializedName

data class PokemonDescriptionOfficial(
    @SerializedName("flavor_text")
    var descricao: String
)