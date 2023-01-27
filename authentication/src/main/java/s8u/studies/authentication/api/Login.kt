package s8u.studies.authentication.api

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import s8u.studies.authentication.model.User

interface Login {
    @GET("api/data/User")
    suspend fun login(): List<User>
}