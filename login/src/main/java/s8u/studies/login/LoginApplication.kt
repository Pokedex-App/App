package s8u.studies.login

import android.app.Application
import org.koin.core.context.startKoin
import s8u.studies.login.modules.loginModule
import s8u.studies.login.modules.signUpModule

class LoginApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setUpKoin()
    }

    private fun setUpKoin() {
        startKoin {
            modules(loginModule, signUpModule)
        }
    }
}