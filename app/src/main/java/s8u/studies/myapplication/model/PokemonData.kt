package s8u.studies.myapplication.model

import s8u.studies.myapplication.model.Pokemon.*
import s8u.studies.myapplication.model.Pokemon.Description.PokemonDescriptionOfficial
import s8u.studies.myapplication.model.Pokemon.abilities.PokemonMoves

class PokemonData(
    id: Int,
    name: String,
    height: String,
    weight: String,
    typeList: ArrayList<PokemonTypes>,
    imgList: PokemonImgList,
    moveList: ArrayList<PokemonMoves>,
    var pastEvolution: PokemonEvolutions?,
    var descriptionList: ArrayList<PokemonDescriptionOfficial>,
) : Pokemon(id = id,
    name = name,
    height = height,
    weight = weight,
    typeList = typeList,
    imgList = imgList,
    movesList = moveList)