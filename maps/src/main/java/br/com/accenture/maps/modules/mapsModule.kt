package br.com.accenture.maps.modules

import br.com.accenture.maps.viewModel.MapsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val mapsModules = module {
    viewModel {
        MapsViewModel()
    }
}
