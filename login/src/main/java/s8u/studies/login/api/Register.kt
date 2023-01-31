package s8u.studies.login.api

import retrofit2.http.Body
import retrofit2.http.POST
import s8u.studies.login.model.User

interface Register {
    @POST("api/services/RegisterService/register")
    suspend fun register(@Body User:User): String
}