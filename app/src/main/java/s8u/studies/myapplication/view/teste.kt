package s8u.studies.myapplication.view

import android.util.Log
import kotlinx.coroutines.runBlocking
import s8u.studies.myapplication.api.*
import s8u.studies.myapplication.di.RetrofitObject
import s8u.studies.myapplication.model.Pokedex.PokedexEntries
import s8u.studies.myapplication.model.Pokemon.Description.PokemonDescription
import s8u.studies.myapplication.model.Pokemon.Pokemon
import s8u.studies.myapplication.model.Pokemon.PokemonTypeEnd

fun main() {
    val response = RetrofitObject.createNetworkService<PokedexEndpoint>()

    runBlocking {
        val list = response.getPokedex("1")
        println(
            list.entriesList[0]
        )

    }
}