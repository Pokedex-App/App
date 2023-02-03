package s8u.studies.login.viewModel

import androidx.lifecycle.ViewModel
import com.google.android.material.textfield.TextInputEditText
import s8u.studies.login.model.User
import s8u.studies.login.repository.SignUpRepository

class SignUpViewModel(private val repository: SignUpRepository) : ViewModel() {
    suspend fun createAccount(name: String, email: String, password: String, gender: String) {
        repository.setUser(
            User(
                name = name,
                email = email,
                password = password,
                characterGender = gender
            )
        )
    }

    suspend fun verifyEmailExist(
        email: String,
        emailExist: () -> Unit,
        emailDoesNotExist: () -> Unit
    ) {
        val user = repository.getUser()
        if (user.isEmpty()) { emailDoesNotExist() }
        else {
            for (i in user.indices) {
                if (user[i].email == email) {
                    emailExist(); break
                } else {
                    if (i == user.size - 1) emailDoesNotExist()
                    else continue
                }
            }
        }
    }

    fun validatesText(text: String, behavior: () -> Unit, elseBehavior: () -> Unit) {
        if (text.isNotEmpty()) behavior()
        else elseBehavior()
    }

    fun validatesButtonState(
        buttonCheck: ArrayList<Boolean>,
        behavior: () -> Unit,
        elseBehavior: () -> Unit
    ) {
        val buttonToCompare = arrayListOf(true, true, true, true)
        if (buttonCheck == buttonToCompare) behavior()
        else elseBehavior()
    }

    fun verifyField(field: TextInputEditText, behavior: () -> Unit, elseBehavior: () -> Unit) {
        if (field.inputType == 33) behavior()
        else elseBehavior()
    }

    fun validateAmountCharacters(text: String, behavior: () -> Unit, elseBehavior: () -> Unit) {
        if (text.length < 8) behavior()
        else elseBehavior()
    }

    fun validateEmailFormat(text: String, behavior: () -> Unit, elseBehavior: () -> Unit) {
        if (text.indexOf("@") < 0 || text.indexOf(".") < 0) behavior()
        else elseBehavior()
    }

    fun lastValidation(boolean: ArrayList<Boolean>, behavior: () -> Unit) {
        if (boolean == arrayListOf(true, true)) behavior()
    }
}