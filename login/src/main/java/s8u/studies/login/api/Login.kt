package s8u.studies.login.api

import retrofit2.http.GET
import s8u.studies.login.model.User

interface Login {
    @GET("api/data/User")
    suspend fun getUser(): List<User>
}