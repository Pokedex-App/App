package s8u.studies.myapplication.view

import android.util.Log
import kotlinx.coroutines.runBlocking
import s8u.studies.myapplication.api.*
import s8u.studies.myapplication.di.RetrofitObject
import s8u.studies.myapplication.model.Pokemon.PokemonTypeEnd

fun main() {
    val response = RetrofitObject.createNetworkService<PokemonAbilityEndpoint>()

    runBlocking {
        val nameAbility = response.getAbility("razor-wind")
        println(nameAbility.flavorTextEntries[0].flavorText)
        println(nameAbility.damage.nameDamage)
        println(nameAbility.power)
        println(nameAbility.type.nameType)
    }
}