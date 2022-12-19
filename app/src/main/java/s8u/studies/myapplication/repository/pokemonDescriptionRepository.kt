package s8u.studies.myapplication.repository

import s8u.studies.myapplication.api.PokemonDescriptionEndpoint

open class PokemonDescriptionRepository (val pokemonEndpoint: PokemonDescriptionEndpoint, val id:String) {

    open suspend fun getPokemonDescription() = pokemonEndpoint.getPokemon(id)
}