package s8u.studies.myapplication.repository

import s8u.studies.myapplication.api.PokemonAbilityEndpoint
import s8u.studies.myapplication.api.PokemonDescriptionEndpoint
import s8u.studies.myapplication.api.PokemonEndpoint

open class DescriptionRepository(
    private val pokemonEndpoint: PokemonEndpoint,
    private val pokemonDescriptionEndpoint: PokemonDescriptionEndpoint,
    private val pokemonAbilityEndpoint: PokemonAbilityEndpoint
) {
    open suspend fun getPokemon(id: String) = pokemonEndpoint.getPokemon(id)
    open suspend fun getPokemonDesc(id: String) = pokemonDescriptionEndpoint.getPokemon(id)
    open suspend fun getPokemonAbility(id: String) = pokemonAbilityEndpoint.getAbility(id)
}