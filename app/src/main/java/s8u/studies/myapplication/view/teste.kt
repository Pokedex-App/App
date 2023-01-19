package s8u.studies.myapplication.view

import kotlinx.coroutines.runBlocking
import s8u.studies.myapplication.api.*
import s8u.studies.myapplication.di.RetrofitObject

fun main() {
    val response = RetrofitObject.createNetworkService<PokemonEndpoint>()
    val response2 = RetrofitObject.createNetworkService<PokemonDescriptionEndpoint>()

    runBlocking {
        val poke = response.getPokemon("1")
        val pokeDesc = response2.getPokemon("1")
        println(
            """
                ${poke.id}
                ${poke.name}
                ${poke.height}
                ${poke.weight}
                ----------------------------------
                ${poke.typeList}
                ----------------------------------
                ${poke.movesList}
                ----------------------------------
                ${poke.imgList}
                ----------------------------------
                ${pokeDesc.descriptionList}
                ----------------------------------
                ${pokeDesc.pastEvolution}
            """.trimIndent()
        )
    }
}