package s8u.studies.login.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import s8u.studies.login.di.RetrofitObject
import s8u.studies.login.repository.LoginRepository
import s8u.studies.login.viewModel.LoginViewModel

val loginModule = module {
    factory {
        LoginRepository(RetrofitObject.createNetworkService())
    }
    viewModel {
        LoginViewModel(get())
    }
}