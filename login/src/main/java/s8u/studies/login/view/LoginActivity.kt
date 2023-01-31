package s8u.studies.login.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import s8u.studies.login.R
import s8u.studies.login.databinding.ActivityLoginBinding
import s8u.studies.login.viewModel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModel()
    private var buttonVerifies = arrayListOf(false, false)
    private var lastValidationField = arrayListOf(false, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        validateFieldsRealTime()
        onClick()
    }

    private fun onClick() {
        binding.textViewNewAccount.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        binding.buttonLogin.setOnClickListener {
            validateFieldOnClick(binding.editTextEmail)
            validateFieldOnClick(binding.editTextPassword)
            viewModel.lastValidation(lastValidationField) {
                validateInformation()
            }
        }
    }

    private fun validateInformation() {
        // Aqui deverá ter a validação por parte da API
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun validateFieldOnClick(editText: TextInputEditText) {
        viewModel.validateAmountCharacters (
            editText.text.toString(),
            {
                val layout = findViewById<TextInputLayout>(viewModel.verifyField(editText))
                layout.error = getString(R.string.input_error)
                viewModel.verifyField(editText,
                    {lastValidationField[0] = false},
                    {lastValidationField[1] = false}
                )
            },
            {
                viewModel.verifyField(editText,
                    {lastValidationField[0] = true},
                    {lastValidationField[1] = true}
                )
            }
        )
    }

    private fun validateFieldsRealTime() {
        binding.editTextEmail.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {
                    viewModel.validatesText(
                        binding.editTextEmail.text.toString(),
                        {
                            buttonVerifies[0] = true; buttonState()
                            binding.outlinedTextFieldEmail.isHintEnabled = false
                            binding.outlinedTextFieldEmail.isErrorEnabled = false
                        },
                        {
                            buttonVerifies[0] = false; buttonState()
                            binding.outlinedTextFieldEmail.isHintEnabled = true
                            binding.outlinedTextFieldEmail.isErrorEnabled = false
                        }
                    )
                }
            }
        )
        binding.editTextPassword.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {
                    viewModel.validatesText(
                        binding.editTextPassword.text.toString(),
                        {
                            buttonVerifies[1] = true; buttonState()
                            binding.outlinedTextFieldPassword.isHintEnabled = false
                            binding.outlinedTextFieldPassword.isErrorEnabled = false
                        },
                        {
                            buttonVerifies[1] = false; buttonState()
                            binding.outlinedTextFieldPassword.isHintEnabled = true
                            binding.outlinedTextFieldPassword.isErrorEnabled = false
                        }
                    )
                }
            }
        )
    }

    fun buttonState() {
        viewModel.validatesButtonState(buttonVerifies,
            {
                binding.buttonLogin.isClickable = true
                binding.buttonLogin.background = getDrawable(R.drawable.background_button_filled)
            },
            {
                binding.buttonLogin.isClickable = false
                binding.buttonLogin.background = getDrawable(R.drawable.background_button_none)
            }
        )
    }
}