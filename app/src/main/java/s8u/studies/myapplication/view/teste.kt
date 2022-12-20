package s8u.studies.myapplication.view

import kotlinx.coroutines.runBlocking
import s8u.studies.myapplication.api.PokemonDescriptionEndpoint
import s8u.studies.myapplication.api.PokemonEndpoint
import s8u.studies.myapplication.di.RetrofitObject

fun main() {

    val possibleResponse = RetrofitObject.createNetworkService<PokemonEndpoint>()
    val anotherPossibleResponse = RetrofitObject.createNetworkService<PokemonDescriptionEndpoint>()

    val Idpokemon = "52"

    runBlocking {

        val pokemon = possibleResponse.getPokemon(Idpokemon)
       // val pokemonDescription = anotherPossibleResponse.getPokemon(Idpokemon)

            println(pokemon.movesList)
       // println(pokemonDescription)
    }
}