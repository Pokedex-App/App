package s8u.studies.myapplication.api

import retrofit2.http.GET
import s8u.studies.myapplication.model.Pokedex.Pokedex

interface PokedexEndpoint {
    @GET("pokedex/1")
    suspend fun getPokedex(): Pokedex
}