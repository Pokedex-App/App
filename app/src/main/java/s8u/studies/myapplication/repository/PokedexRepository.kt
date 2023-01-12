package s8u.studies.myapplication.repository

import s8u.studies.myapplication.api.PokedexEndpoint
import s8u.studies.myapplication.api.PokedexTypeEndpoint
import s8u.studies.myapplication.api.PokemonTypeEndpoint

open class PokedexRepository(
    private val pokedexEndpoint: PokedexEndpoint,
    private val pokedexTypeEndpoint: PokedexTypeEndpoint,
    private val pokemonTypeEndpoint: PokemonTypeEndpoint
) {
    open suspend fun getPokedex(id: String) = pokedexEndpoint.getPokedex(id)
    open suspend fun getPokedexType(id: String) = pokedexTypeEndpoint.getPokemon(id)
    open suspend fun getPokemonType(id: String) = pokemonTypeEndpoint.getPokemon(id)
}