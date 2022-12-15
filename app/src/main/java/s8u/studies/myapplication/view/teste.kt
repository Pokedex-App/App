package s8u.studies.myapplication.view

import kotlinx.coroutines.runBlocking
import s8u.studies.myapplication.api.pokedexEndpoint
import s8u.studies.myapplication.api.pokemonDescriptionEndpoint
import s8u.studies.myapplication.api.pokemonEndpoint
import s8u.studies.myapplication.di.retrofitObject
import s8u.studies.myapplication.model.Pokedex

fun main() {

    val possibleResponse = retrofitObject.createNetworkService<pokemonEndpoint>()
    val anotherPossibleResponse = retrofitObject.createNetworkService<pokemonDescriptionEndpoint>()

    val Idpokemon = "2"

    runBlocking {

        val pokemon = possibleResponse.getPokemon(Idpokemon)
        val pokemonDescription = anotherPossibleResponse.getPokemon(Idpokemon)

            println(pokemon)
        println(pokemonDescription)
    }
}