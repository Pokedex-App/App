package s8u.studies.login.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.textfield.TextInputEditText
import s8u.studies.login.R
import s8u.studies.login.repository.LoginRepository

class LoginViewModel (private val repository: LoginRepository) : ViewModel() {
    val livedataTest = MutableLiveData<Unit>()
    private val validation = arrayListOf(false, false)

    fun lastValidation(boolean: ArrayList<Boolean>, behavior: () -> Unit) {
        if (boolean == arrayListOf(true, true)) behavior()
    }

    fun validatesText(text: String, behavior: () -> Unit, elseBehavior: () -> Unit) {
        if (text.isNotEmpty())  behavior()
        else elseBehavior()
    }

    fun verifyField(field: TextInputEditText) : Int {
        return if (field.inputType == 33) R.id.outlinedTextField_email
        else R.id.outlinedTextField_password
    }

    fun verifyField(field: TextInputEditText, behavior: () -> Unit, elseBehavior: () -> Unit) {
        if (field.inputType == 33) behavior()
        else elseBehavior()
    }

    fun validatesButtonState(buttonCheck: ArrayList<Boolean>, behavior: () -> Unit, elseBehavior: () -> Unit) {
        if (buttonCheck == arrayListOf(true, true)) behavior()
        else elseBehavior()
    }

    fun validateAmountCharacters(text: String, behavior: () -> Unit, elseBehavior: () -> Unit) {
        if (text.length < 8) behavior()
        else elseBehavior()
    }
}