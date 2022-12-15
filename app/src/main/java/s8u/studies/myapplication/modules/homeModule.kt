package s8u.studies.myapplication.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import s8u.studies.myapplication.di.retrofitObject
import s8u.studies.myapplication.model.Pokedex
import s8u.studies.myapplication.repository.pokedexRepository
import s8u.studies.myapplication.viewModel.HomeViewModel

val networkModule = module {
    viewModel {
        HomeViewModel(pokedexRepository(retrofitObject.createNetworkService()))
    }
}