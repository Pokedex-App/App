package s8u.studies.myapplication.repository

import s8u.studies.myapplication.api.pokedexEndpoint
import s8u.studies.myapplication.api.pokemonDescriptionEndpoint
import s8u.studies.myapplication.api.pokemonEndpoint
import s8u.studies.myapplication.model.PokemonDescription

open class pokemonDescriptionRepository (val pokemonEndpoint: pokemonDescriptionEndpoint, val id:String) {

    open suspend fun getPokemonDescription() = pokemonEndpoint.getPokemon(id)
}