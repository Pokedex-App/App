package s8u.studies.myapplication.api

import retrofit2.http.GET
import retrofit2.http.Path
import s8u.studies.myapplication.model.Pokedex.PokemonTypes

interface PokemonTypeEndpoint {
    @GET("type/{id}")
    suspend fun getPokemon(@Path("id") id: String): PokemonTypes
}