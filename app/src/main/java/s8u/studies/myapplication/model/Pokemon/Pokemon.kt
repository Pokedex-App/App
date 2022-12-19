package s8u.studies.myapplication.model.Pokemon

import com.google.gson.annotations.SerializedName

open class Pokemon(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name:String,
    @SerializedName("height")
    var height: String,
    @SerializedName("weight")
    var weight: String,
    @SerializedName("types")
    var typeList: ArrayList<PokemonTypes>,
    @SerializedName("sprites")
    var imgList: PokemonImgList,
    @SerializedName("moves")
    var movesList: ArrayList<PokemonMoves>
)