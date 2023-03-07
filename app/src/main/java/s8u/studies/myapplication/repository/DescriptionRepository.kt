package s8u.studies.myapplication.repository

import s8u.studies.myapplication.api.PokemonAbilityEndpoint
import s8u.studies.myapplication.api.PokemonDescriptionEndpoint
import s8u.studies.myapplication.api.PokemonEndpoint
import s8u.studies.myapplication.di.Resource
import s8u.studies.myapplication.di.ResponseHandler
import s8u.studies.myapplication.model.Pokedex.Pokedex
import s8u.studies.myapplication.model.Pokemon.Description.PokemonDescription
import s8u.studies.myapplication.model.Pokemon.Pokemon
import s8u.studies.myapplication.model.Pokemon.abilities.PokemonAbilityInformation

open class DescriptionRepository(
    private val pokemonEndpoint: PokemonEndpoint,
    private val pokemonDescriptionEndpoint: PokemonDescriptionEndpoint,
    private val pokemonAbilityEndpoint: PokemonAbilityEndpoint,
    private val responseHandler: ResponseHandler
) {
    open suspend fun getPokemon(id: String) : Resource<Pokemon> {
        return try {
            responseHandler.handleSuccess(pokemonEndpoint.getPokemon(id))
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
    open suspend fun getPokemonDesc(id: String) : Resource<PokemonDescription> {
        return try {
            responseHandler.handleSuccess(pokemonDescriptionEndpoint.getPokemon(id))
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
    open suspend fun getPokemonAbility(id: String) : Resource<PokemonAbilityInformation> {
        return try {
            responseHandler.handleSuccess(pokemonAbilityEndpoint.getAbility(id))
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}