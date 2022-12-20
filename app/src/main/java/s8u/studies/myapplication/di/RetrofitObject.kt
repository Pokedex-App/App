package s8u.studies.myapplication.di

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import s8u.studies.myapplication.network.LoggingInterceptor

object RetrofitObject {
    inline fun <reified T> createNetworkService(): T {

        val log = LoggingInterceptor().getInterceptor()

        val client = OkHttpClient
            .Builder()
            .addInterceptor(log)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()

        return retrofit.create(T::class.java)
    }

}