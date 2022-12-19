package s8u.studies.myapplication.api

import retrofit2.http.GET
import retrofit2.http.Path
import s8u.studies.myapplication.model.Pokemon.Pokemon

interface PokemonEndpoint {
    @GET("pokemon/{id}")
    suspend fun getPokemon(@Path("id")id:String): Pokemon
}