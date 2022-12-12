package s8u.studies.myapplication.view

import kotlinx.coroutines.runBlocking
import s8u.studies.myapplication.api.pokedexEndpoint
import s8u.studies.myapplication.api.pokemonEndpoint
import s8u.studies.myapplication.di.retrofitObject
import s8u.studies.myapplication.model.Pokedex

fun main() {

    val possibleResponse = retrofitObject.createNetworkService<pokedexEndpoint>()

//    val pokemon = "1"

    runBlocking {

        val pokemon = possibleResponse.getPokedex()

        for(i in 0 .. pokemon.entriesList.size - 1){
            println(pokemon.entriesList.get(i).id)
            println(pokemon.entriesList.get(i).pokemonSpecies.pokemonName)
        }
    }
}