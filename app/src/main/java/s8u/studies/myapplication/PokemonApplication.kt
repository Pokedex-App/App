package s8u.studies.myapplication

import android.app.Application
import org.koin.core.context.startKoin
import s8u.studies.myapplication.modules.descriptionModule
import s8u.studies.myapplication.modules.networkModule

class PokemonApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setKoinUp()
    }

    private fun setKoinUp() {
        startKoin {
            modules(networkModule, descriptionModule)
        }
    }
}