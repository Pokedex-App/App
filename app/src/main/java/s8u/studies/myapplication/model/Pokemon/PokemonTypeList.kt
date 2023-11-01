package s8u.studies.myapplication.model.Pokemon

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PokemonTypeList(
    @SerializedName("name")
    var name: String
) : Serializable