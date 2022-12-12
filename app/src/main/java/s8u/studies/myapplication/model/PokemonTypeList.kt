package s8u.studies.myapplication.model

import com.google.gson.annotations.SerializedName

data class PokemonTypeList (
    @SerializedName("name")
    var name:String
        )