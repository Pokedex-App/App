package s8u.studies.login.viewModel

import androidx.lifecycle.ViewModel
import com.google.android.material.textfield.TextInputEditText
import s8u.studies.login.repository.LoginRepository

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    suspend fun verifyEmailPassword(
        email: String,
        password: String,
        behavior: () -> Unit,
        behaviorEmail: () -> Unit,
        behaviorPassword: () -> Unit
    ) {
        val user = repository.getUser()

        for (i in user.indices) {
            if (email == user[i].email) {
                if (password == user[i].password) behavior()
                else behaviorPassword()
            } else {
                if (i == user.size - 1) behaviorEmail()
                else continue
            }
        }
    }

    fun lastValidation(boolean: ArrayList<Boolean>, behavior: () -> Unit) {
        if (boolean == arrayListOf(true, true)) behavior()
    }

    fun validatesText(text: String, behavior: () -> Unit, elseBehavior: () -> Unit) {
        if (text.isNotEmpty()) behavior()
        else elseBehavior()
    }

    fun verifyField(field: TextInputEditText, behavior: () -> Unit, elseBehavior: () -> Unit) {
        if (field.inputType == 33) behavior()
        else elseBehavior()
    }

    fun validatesButtonState(
        buttonCheck: ArrayList<Boolean>,
        behavior: () -> Unit,
        elseBehavior: () -> Unit
    ) {
        if (buttonCheck == arrayListOf(true, true)) behavior()
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
}