package s8u.studies.login.repository

import s8u.studies.login.api.Register
import s8u.studies.login.model.User

class SignUpRepository (private val registerEndpoint: Register) {
    suspend fun getUser(user: User) = registerEndpoint.register(user)
}