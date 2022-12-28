package s8u.studies.myapplication.view

import android.util.Log
import kotlinx.coroutines.runBlocking
import s8u.studies.myapplication.api.*
import s8u.studies.myapplication.di.RetrofitObject
import s8u.studies.myapplication.model.Pokemon.Description.PokemonDescription
import s8u.studies.myapplication.model.Pokemon.Pokemon
import s8u.studies.myapplication.model.Pokemon.PokemonTypeEnd

fun main() {
    val response = RetrofitObject.createNetworkService<PokemonDescriptionEndpoint>()

    runBlocking {
        val nameAbility = response.getPokemon("230")
        println(nameAbility.DescriptionList[0].descricao.replace("\n"," "))

    }
}