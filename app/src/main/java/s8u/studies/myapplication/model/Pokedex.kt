package s8u.studies.myapplication.model

import com.google.gson.annotations.SerializedName

data class Pokedex (
        @SerializedName("entry_number")
        var id:String,

        @SerializedName("name")
        var nome:String
        )