package s8u.studies.login.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.runBlocking
import org.koin.androidx.viewmodel.ext.android.viewModel
import s8u.studies.login.R
import s8u.studies.login.databinding.ActivitySignUpBinding
import s8u.studies.login.viewModel.SignUpViewModel

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: SignUpViewModel by viewModel()
    private var buttonVerifies = arrayListOf(false, false, false, false)
    private var lastValidationField = arrayListOf(false, false)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        autoCompleteConfig()
        validateFieldsRealTime()
        onClick()
    }

    private fun onClick() {
        binding.textViewHasAccount.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.buttonSignUp.setOnClickListener {
            validateFieldsOnClick(binding.editTextEmail)
            validateFieldsOnClick(binding.editTextPassword)
            viewModel.lastValidation(lastValidationField) {
                runBlocking {
                    viewModel.createAccount(
                        binding.editTextName.text.toString(),
                        binding.editTextEmail.text.toString(),
                        binding.editTextPassword.text.toString(),
                        binding.autoCompleteCharacterGender.text.toString()
                    )
                }
                startActivity(Intent(this, LoginActivity::class.java))
                Log.i(
                    "Register",
                    "\nName: ${binding.editTextName.text}" +
                            "\nEmail: ${binding.editTextEmail.text}" +
                            "\nPassword: ${binding.editTextPassword.text}" +
                            "\nGender: ${binding.autoCompleteCharacterGender.text}"
                )
            }
        }
    }

    private fun validateFieldsOnClick(editText: TextInputEditText) {
        viewModel.verifyField(editText,
            {
                viewModel.validateEmailFormat(editText.text.toString(),
                    {
                        binding.outlinedTextFieldEmail.error = getString(R.string.input_error_email)
                        lastValidationField[0] = false
                    }, { lastValidationField[0] = true }
                )
            },
            {
                viewModel.validateAmountCharacters(editText.text.toString(),
                    {
                        binding.outlinedTextFieldPassword.error = getString(R.string.input_error_password)
                        lastValidationField[1] = false
                    }, { lastValidationField[1] = true }
                )
            }
        )
    }

    private fun validateFieldsRealTime() {
        binding.editTextName.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {
                    viewModel.validatesText(
                        binding.editTextName.text.toString(),
                        {
                            buttonVerifies[0] = true; buttonState()
                            binding.outlinedTextFieldName.isHintEnabled = false
                            binding.outlinedTextFieldName.isErrorEnabled = false
                        },
                        {
                            buttonVerifies[0] = false; buttonState()
                            binding.outlinedTextFieldName.isHintEnabled = true
                            binding.outlinedTextFieldName.isErrorEnabled = false
                        }
                    )
                }
            }
        )
        binding.autoCompleteCharacterGender.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {
                    viewModel.validatesText(
                        binding.autoCompleteCharacterGender.text.toString(),
                        {
                            buttonVerifies[1] = true; buttonState()
                            binding.outlinedTextFieldCharacterGender.isHintEnabled = false
                            binding.outlinedTextFieldCharacterGender.isErrorEnabled = false
                        },
                        {
                            buttonVerifies[1] = false; buttonState()
                            binding.outlinedTextFieldCharacterGender.isHintEnabled = true
                            binding.outlinedTextFieldCharacterGender.isErrorEnabled = false
                        }
                    )
                }
            }
        )
        binding.editTextEmail.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {
                    viewModel.validatesText(
                        binding.editTextEmail.text.toString(),
                        {
                            buttonVerifies[2] = true; buttonState()
                            binding.outlinedTextFieldEmail.isHintEnabled = false
                            binding.outlinedTextFieldEmail.isErrorEnabled = false
                        },
                        {
                            buttonVerifies[2] = false; buttonState()
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
                            buttonVerifies[3] = true; buttonState()
                            binding.outlinedTextFieldPassword.isHintEnabled = false
                            binding.outlinedTextFieldPassword.isErrorEnabled = false
                        },
                        {
                            buttonVerifies[3] = false; buttonState()
                            binding.outlinedTextFieldPassword.isHintEnabled = true
                            binding.outlinedTextFieldPassword.isErrorEnabled = false
                        }
                    )
                }
            }
        )
    }

    private fun buttonState() {
        viewModel.validatesButtonState(buttonVerifies,
            {
                binding.buttonSignUp.isClickable = true
                binding.buttonSignUp.background = getDrawable(R.drawable.background_button_filled)
            },
            {
                binding.buttonSignUp.isClickable = false
                binding.buttonSignUp.background = getDrawable(R.drawable.background_button_none)
            }
        )
    }

    private fun autoCompleteConfig() {
        val genders = resources.getStringArray(R.array.character_gender)
        val adapter = ArrayAdapter(this, R.layout.list_item, genders)
        binding.autoCompleteCharacterGender.apply {
            setAdapter(adapter)
            setOnItemClickListener { _, _, _, _ ->
                binding.outlinedTextFieldCharacterGender.isHintEnabled = false
            }
        }
    }
}