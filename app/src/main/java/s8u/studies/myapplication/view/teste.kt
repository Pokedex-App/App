package s8u.studies.myapplication.view

import android.util.Log
import kotlinx.coroutines.runBlocking
import s8u.studies.myapplication.api.PokedexEndpoint
import s8u.studies.myapplication.api.PokemonDescriptionEndpoint
import s8u.studies.myapplication.api.PokemonEndpoint
import s8u.studies.myapplication.api.PokedexTypeEndpoint
import s8u.studies.myapplication.di.RetrofitObject
import s8u.studies.myapplication.model.Pokemon.PokemonTypeEnd

fun main() {

    val AndAnotherONEpossibleResponse = RetrofitObject.createNetworkService<PokedexEndpoint>()
    val possibleResponse = RetrofitObject.createNetworkService<PokemonEndpoint>()
    val anotherPossibleResponse = RetrofitObject.createNetworkService<PokemonDescriptionEndpoint>()
    val andAnotherPossibleResponse = RetrofitObject.createNetworkService<PokedexTypeEndpoint>()

    val Idpokemon = "52"

    var a = arrayListOf<PokemonTypeEnd>()

    runBlocking {

        val pokemon = AndAnotherONEpossibleResponse.getPokedex()
        //val a = pokemon.entriesList[386].pokedexSpecies.pokemonName
        if(pokemon.entriesList.size > 400) {
            print(pokemon.entriesList[385].pokedexSpecies.toString())
            //val pokemonDescription = possibleResponse.getPokemon(pokemon.entriesList[386].pokedexSpecies.pokemonName)
            //print(pokemonDescription.weight.toString())
        }


//        for(i in 0..pokemon.entriesList.size - 30){
//            a.add(andAnotherPossibleResponse.getPokemon(pokemon.entriesList[i].id.toString()))
//
//        }
           //pokemon.entriesList[386].pokedexSpecies.pokemonName
      //  Log.i("Teste",pokemonDescription.toString())
    }
}