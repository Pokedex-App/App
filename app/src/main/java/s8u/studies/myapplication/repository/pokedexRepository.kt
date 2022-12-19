package s8u.studies.myapplication.repository

import s8u.studies.myapplication.api.PokedexEndpoint

open class PokedexRepository (val pokedexEndpoint: PokedexEndpoint) {
        open suspend fun getPokedex() = pokedexEndpoint.getPokedex()
}