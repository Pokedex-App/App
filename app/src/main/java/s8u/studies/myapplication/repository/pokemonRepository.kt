package s8u.studies.myapplication.repository

import s8u.studies.myapplication.api.PokemonEndpoint

open class PokemonRepository (val pokemonEndpoint: PokemonEndpoint) {

      // open suspend fun getPokedex() = PokemonEndpoint.getPokemon()
}