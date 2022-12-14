package s8u.studies.myapplication.repository

import s8u.studies.myapplication.api.pokedexEndpoint
import s8u.studies.myapplication.api.pokemonEndpoint

open class pokemonRepository (val pokemonEndpoint: pokemonEndpoint) {

      // open suspend fun getPokedex() = pokemonEndpoint.getPokemon()
}