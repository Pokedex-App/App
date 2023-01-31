package s8u.studies.login.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import s8u.studies.login.repository.SignUpRepository

class SignUpViewModel(private val repository: SignUpRepository) : ViewModel() {
    private val _buttonStateLiveData = MutableLiveData<Boolean>()
    val buttonStateLiveData: LiveData<Boolean> = _buttonStateLiveData

    fun validatesText(text: String, behavior: () -> Unit, elseBehavior: () -> Unit) {
        if (text.isNotEmpty())  behavior()
        else elseBehavior()
    }

    fun validatesButtonState(buttonCheck: ArrayList<Boolean>, behavior: () -> Unit, elseBehavior: () -> Unit) {
        val buttonToCompare = arrayListOf(true, true, true, true)
        if (buttonCheck == buttonToCompare) behavior()
        else elseBehavior()
    }
}