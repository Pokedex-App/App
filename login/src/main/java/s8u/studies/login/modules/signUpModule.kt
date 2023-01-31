package s8u.studies.login.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import s8u.studies.login.di.RetrofitObject
import s8u.studies.login.repository.SignUpRepository
import s8u.studies.login.viewModel.SignUpViewModel

val signUpModule = module {
    factory {
        SignUpRepository(RetrofitObject.createNetworkService())
    }
    viewModel {
        SignUpViewModel(get())
    }
}