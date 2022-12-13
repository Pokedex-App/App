package s8u.studies.myapplication.model

class PokemonData(
    id: Int,
    name: String,
    height: String,
    weight: String,
    typeList: ArrayList<PokemonTypes>,
    imgList: PokemonImgList,
    var pastEvolution: PokemonEvolutions?,
    var descriptionList: ArrayList<PokemonDescriptionOfficial>
) : Pokemon(id = id,
    name = name,
    height = height,
    weight = weight,
    typeList = typeList,
    imgList = imgList)