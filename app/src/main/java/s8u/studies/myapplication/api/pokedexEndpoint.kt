package s8u.studies.myapplication.api

import retrofit2.http.GET
import s8u.studies.myapplication.model.Pokedex
import s8u.studies.myapplication.model.Pokemon_entries

interface pokedexEndpoint {
    @GET("pokedex/1")
    suspend fun getPokedex(): Pokedex
}