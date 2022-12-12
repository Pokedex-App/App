package s8u.studies.myapplication.model

import com.google.gson.annotations.SerializedName

data class PokemonDescription(
    @SerializedName("flavor_text_entries")
    var DescriptionList: ArrayList<PokemonDescriptionOfficial>,
    @SerializedName("evolves_from_species")
    var evolucaoAnterior: String
)