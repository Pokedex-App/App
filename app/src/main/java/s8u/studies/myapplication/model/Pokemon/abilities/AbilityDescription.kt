package s8u.studies.myapplication.model.Pokemon.abilities

import com.google.gson.annotations.SerializedName

data class AbilityDescription(
    @SerializedName("flavor_text")
    var flavorText: String
)
