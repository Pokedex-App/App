package s8u.studies.myapplication.model.Pokemon

import com.google.gson.annotations.SerializedName
import s8u.studies.myapplication.model.Pokemon.PokemonImg

data class PokemonImgOfficial (
    @SerializedName("official-artwork")
     var type: PokemonImg
        )