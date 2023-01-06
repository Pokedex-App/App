package s8u.studies.myapplication.api

import retrofit2.http.GET
import retrofit2.http.Path
import s8u.studies.myapplication.model.Pokedex.Pokedex

interface PokedexEndpoint {
    @GET("pokedex/{id}")
    suspend fun getPokedex(@Path("id")id:String): Pokedex
}