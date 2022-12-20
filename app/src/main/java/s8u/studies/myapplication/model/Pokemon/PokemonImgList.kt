package s8u.studies.myapplication.model.Pokemon

import com.google.gson.annotations.SerializedName

data class PokemonImgList (
    @SerializedName("other")
    var imgList: PokemonImgOfficial
        )