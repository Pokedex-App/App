package s8u.studies.myapplication.model

import com.google.gson.annotations.SerializedName

data class PokemonImgOfficial (
    @SerializedName("official-artwork")
     var type: PokemonImg
        )