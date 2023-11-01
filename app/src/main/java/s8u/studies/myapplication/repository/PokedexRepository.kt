package s8u.studies.myapplication.repository

import s8u.studies.myapplication.api.PokedexEndpoint
import s8u.studies.myapplication.api.PokedexTypeEndpoint
import s8u.studies.myapplication.api.PokemonTypeEndpoint
import s8u.studies.myapplication.di.Resource
import s8u.studies.myapplication.di.ResponseHandler
import s8u.studies.myapplication.model.Pokedex.Pokedex
import s8u.studies.myapplication.model.Pokedex.PokedexTypes
import s8u.studies.myapplication.model.Pokedex.PokemonTypes

open class PokedexRepository(
    private val pokedexEndpoint: PokedexEndpoint,
    private val pokedexTypeEndpoint: PokedexTypeEndpoint,
    private val pokemonTypeEndpoint: PokemonTypeEndpoint,
    private val responseHandler: ResponseHandler
) {
    open suspend fun getPokedex(id: String): Resource<Pokedex> {
        return try {
            responseHandler.handleSuccess(pokedexEndpoint.getPokedex(id))
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
    open suspend fun getPokedexType(id: String): Resource<s8u.studies.myapplication.model.Pokemon.PokedexTypes> {
        return try {
            responseHandler.handleSuccess(pokedexTypeEndpoint.getPokemon(id))
        } catch (e: Exception) {
           responseHandler.handleException(e)
        }
    }
    open suspend fun getPokemonType(id: String) : Resource<PokemonTypes> {
        return try {
            responseHandler.handleSuccess(pokemonTypeEndpoint.getPokemon(id))
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}