package s8u.studies.login.repository

import s8u.studies.login.api.Login
import s8u.studies.login.api.Register
import s8u.studies.login.model.User

class SignUpRepository (private val registerEndpoint: Register, private val loginEndpoint: Login) {
    suspend fun setUser(user: User) = registerEndpoint.register(user)
    suspend fun getUser() = loginEndpoint.getUser()
}