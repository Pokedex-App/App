package s8u.studies.myapplication.model.Pokemon.abilities

import com.google.gson.annotations.SerializedName

data class AbilityType(
    @SerializedName("name")
    var nameType: String
)
