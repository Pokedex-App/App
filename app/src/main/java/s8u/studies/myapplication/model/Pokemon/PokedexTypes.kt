package s8u.studies.myapplication.model.Pokemon

import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class PokedexTypes(
    @SerializedName("types")
    var typeList: ArrayList<PokemonTypes>,
    @SerializedName("id")
    var id: String
) : Serializable {
    override fun toString(): String {
        return String.format(
            """
                id: %s | type: %s
            """.trimIndent(),
            id, typeList
        )
    }
}