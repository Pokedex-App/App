package s8u.studies.myapplication.model

import com.google.gson.annotations.SerializedName

data class Pokemon (
    @SerializedName("id")
    var id:Int,
   @SerializedName("name")
   var nome:String,
   @SerializedName("height")
   var altura:String,
   @SerializedName("weight")
   var peso:String,
    @SerializedName("type")
    var type:String
        )