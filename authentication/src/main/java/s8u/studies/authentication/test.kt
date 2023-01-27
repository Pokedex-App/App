package s8u.studies.authentication

import kotlinx.coroutines.runBlocking
import s8u.studies.authentication.api.Login
import s8u.studies.authentication.api.Register
import s8u.studies.authentication.di.RetrofitObject
import s8u.studies.authentication.model.User

fun main() {
    runBlocking {
        val users = RetrofitObject.createNetworkService<Login>().login()
        var counter = 0
        val email = "JohnMarston@orkut.com"
        val password = "123456"

        for (i in 0 until users.size) {
            if (users[i].email == email && users[i].password == password) {
                counter++
                if (i == users.size - 1) {
                    if (counter == 1) {
                        println("Player ${users[i].name}  personagem Masculino - ${users[i].isCharacterMale}")
                    } else {
                        println("Mais de um Usuário com mesmo login e senha")
                    }
                }
            }
        }


        println(
            RetrofitObject.createNetworkService<Register>().register(
                User(
                    "John",
                    "JohnMarston@orkut.com",
                    "123456",
                    false

                )
            )
        )


    }
}