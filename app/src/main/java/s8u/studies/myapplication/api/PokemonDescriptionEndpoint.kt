package s8u.studies.myapplication.api

import retrofit2.http.GET
import retrofit2.http.Path
import s8u.studies.myapplication.model.Pokemon.Description.PokemonDescription

interface PokemonDescriptionEndpoint {
    @GET("pokemon-species/{id}")
    suspend fun getPokemon(@Path("id")id:String): PokemonDescription
}