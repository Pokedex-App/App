package s8u.studies.myapplication.model.Pokemon.abilities

import com.google.gson.annotations.SerializedName

data class PokemonAbilityInformation(
    @SerializedName("flavor_text_entries")
    var flavorTextEntries: ArrayList<AbilityDescription>,
    @SerializedName("power")
    var power: Int?,
    @SerializedName("type")
    var type: AbilityType,
    @SerializedName("damage_class")
    var damage: AbilityDamage
)
