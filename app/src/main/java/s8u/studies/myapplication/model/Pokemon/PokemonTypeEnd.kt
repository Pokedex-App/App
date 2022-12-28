package s8u.studies.myapplication.model.Pokemon

import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class PokemonTypeEnd(
    @SerializedName("types")
    var typeList: ArrayList<PokemonTypes>,
    @SerializedName("id")
    var id:String
   ):Serializable