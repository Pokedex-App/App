package s8u.studies.myapplication.view

import kotlinx.coroutines.runBlocking
import s8u.studies.myapplication.api.PokedexEndpoint
import s8u.studies.myapplication.api.PokemonDescriptionEndpoint
import s8u.studies.myapplication.api.PokemonEndpoint
import s8u.studies.myapplication.api.PokemonTypeEndpoint
import s8u.studies.myapplication.di.RetrofitObject
import s8u.studies.myapplication.model.Pokemon.PokemonTypeEnd

fun main() {

    val AndAnotherONEpossibleResponse = RetrofitObject.createNetworkService<PokedexEndpoint>()
    val possibleResponse = RetrofitObject.createNetworkService<PokemonEndpoint>()
    val anotherPossibleResponse = RetrofitObject.createNetworkService<PokemonDescriptionEndpoint>()
    val andAnotherPossibleResponse = RetrofitObject.createNetworkService<PokemonTypeEndpoint>()

    val Idpokemon = "52"

    var a = arrayListOf<PokemonTypeEnd>()

    runBlocking {

        val pokemon = AndAnotherONEpossibleResponse.getPokedex()
       // val pokemonDescription = anotherPossibleResponse.getPokemon(Idpokemon)

        for(i in 0..pokemon.entriesList.size - 30){
            a.add(andAnotherPossibleResponse.getPokemon(pokemon.entriesList[i].id.toString()))

        }


    }
}