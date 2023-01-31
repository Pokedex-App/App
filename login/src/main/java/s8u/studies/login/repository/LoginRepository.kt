package s8u.studies.login.repository

import s8u.studies.login.api.Login

class LoginRepository (private val loginEndpoint: Login) {
    suspend fun getUser() = loginEndpoint.getUser()
}