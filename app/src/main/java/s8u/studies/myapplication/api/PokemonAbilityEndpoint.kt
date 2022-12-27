package s8u.studies.myapplication.api

import retrofit2.http.GET
import retrofit2.http.Path
import s8u.studies.myapplication.model.Pokemon.abilities.PokemonAbilityInformation

interface PokemonAbilityEndpoint {
    @GET("move/{id}")
    suspend fun getAbility(@Path("id")id:String): PokemonAbilityInformation
}