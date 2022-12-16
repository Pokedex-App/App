package s8u.studies.myapplication.model

import com.google.gson.annotations.SerializedName

data class PokemonImg (
    @SerializedName("front_default")
     var urlImg: String
        )