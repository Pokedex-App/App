package s8u.studies.login.viewModel

import androidx.lifecycle.ViewModel
import com.google.android.material.textfield.TextInputEditText
import s8u.studies.login.model.User
import s8u.studies.login.repository.SignUpRepository

class SignUpViewModel(private val repository: SignUpRepository) : ViewModel() {
    suspend fun createAccount(name: String, email: String, password: String, gender: String) {
        val booleanGender = gender == "Male"
        repository.setUser(
            User(
               name = name,
               email = email,
               password = password,
               isCharacterMale = booleanGender
            )
        )
    }

    fun validatesText(text: String, behavior: () -> Unit, elseBehavior: () -> Unit) {
        if (text.isNotEmpty())  behavior()
        else elseBehavior()
    }

    fun validatesButtonState(buttonCheck: ArrayList<Boolean>, behavior: () -> Unit, elseBehavior: () -> Unit) {
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