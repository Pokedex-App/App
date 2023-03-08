package s8u.studies.myapplication.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import s8u.studies.myapplication.di.ResponseHandler
import s8u.studies.myapplication.di.RetrofitObject
import s8u.studies.myapplication.repository.DescriptionRepository
import s8u.studies.myapplication.viewModel.DescriptionViewModel

val descriptionModule = module{
    factory {
        DescriptionRepository(
            RetrofitObject.createNetworkService(),
            RetrofitObject.createNetworkService(),
            RetrofitObject.createNetworkService(),
            ResponseHandler()
        )
    }
    viewModel {
        DescriptionViewModel(get())
    }
}