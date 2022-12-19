package s8u.studies.myapplication.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import s8u.studies.myapplication.di.RetrofitObject
import s8u.studies.myapplication.repository.PokedexRepository
import s8u.studies.myapplication.viewModel.HomeViewModel

val networkModule = module {
    viewModel {
        HomeViewModel(PokedexRepository(RetrofitObject.createNetworkService()))
    }
}