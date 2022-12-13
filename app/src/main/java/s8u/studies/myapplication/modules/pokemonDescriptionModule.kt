package s8u.studies.myapplication.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import s8u.studies.myapplication.di.retrofitObject
import s8u.studies.myapplication.repository.pokemonDescriptionRepository

//val networkModule =   module{
//    viewModel {
//     pokemonDescriptionRepository(retrofitObject.createNetworkService())
//    }
//}