package s8u.studies.myapplication.view

import kotlinx.coroutines.runBlocking
import s8u.studies.myapplication.api.*
import s8u.studies.myapplication.di.RetrofitObject
import s8u.studies.myapplication.model.Pokemon.Description.PokemonDescription
import s8u.studies.myapplication.model.Pokemon.Pokemon

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
                ${pokeDesc.DescriptionList}
                ----------------------------------
                ${pokeDesc.pastEvolution}
            """.trimIndent()
        )
    }
}