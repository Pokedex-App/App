package s8u.studies.myapplication.model.Pokemon.Description

import com.google.gson.annotations.SerializedName
import s8u.studies.myapplication.model.Pokemon.PokemonEvolutions

data class PokemonDescription(
    @SerializedName("flavor_text_entries")
    var DescriptionList: ArrayList<PokemonDescriptionOfficial>,
    @SerializedName("evolves_from_species")
    var pastEvolution: PokemonEvolutions
)