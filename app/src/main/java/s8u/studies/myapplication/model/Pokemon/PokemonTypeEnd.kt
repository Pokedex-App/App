package s8u.studies.myapplication.model.Pokemon

import com.google.gson.annotations.SerializedName

open class PokemonTypeEnd(
    @SerializedName("types")
    var typeList: ArrayList<PokemonTypes>
   )