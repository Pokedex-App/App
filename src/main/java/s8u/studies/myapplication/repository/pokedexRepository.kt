package s8u.studies.myapplication.repository

import s8u.studies.myapplication.api.pokedexEndpoint

open class pokedexRepository (val pokedexEndpoint: pokedexEndpoint) {
        open suspend fun getPokedex() = pokedexEndpoint.getPokedex()
}