package s8u.studies.myapplication.view

import kotlinx.coroutines.runBlocking
import s8u.studies.myapplication.api.*
import s8u.studies.myapplication.di.RetrofitObject

fun main() {
    val response = RetrofitObject.createNetworkService<PokemonTypeEndpoint>()

    runBlocking {
        val list = response.getPokemon("1")
        println(
            list.nome + "\n" + list.pokedexSpecies
        )

    }
}