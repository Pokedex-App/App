package s8u.studies.myapplication.api

import retrofit2.http.GET
import retrofit2.http.Path
import s8u.studies.myapplication.model.Pokemon
import s8u.studies.myapplication.model.PokemonDescription

interface pokemonDescriptionEndpoint {
    @GET("pokemon-species/{id}")
    suspend fun getPokemon(@Path("id")id:String): PokemonDescription
}