package s8u.studies.authentication.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import s8u.studies.authentication.model.User

interface Register {
    @POST("api/services/RegisterService/register")
    suspend fun register(@Body User:User): String
}