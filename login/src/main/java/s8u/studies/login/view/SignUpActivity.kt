package s8u.studies.login.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.androidx.viewmodel.ext.android.viewModel
import s8u.studies.login.R
import s8u.studies.login.databinding.ActivitySignUpBinding
import s8u.studies.login.databinding.ModalSuccessBinding
import s8u.studies.login.viewModel.SignUpViewModel

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: SignUpViewModel by viewModel()
    private var buttonVerifies = arrayListOf(false, false, false, false)
    private var lastValidationField = arrayListOf(false, false)
    private lateinit var dialog: AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUp()
        binding.buttonSignUp.isClickable = false
    }

    private fun setUp() {
        autoCompleteConfig()
        validateFieldsRealTime()
        onClick()
    }

    private fun visibilityButton(viewLoading: Int, viewButton: Int, buttonClick: Boolean) {
        binding.gifLoading.visibility = viewLoading
        binding.buttonSignUp.visibility = viewButton
        binding.buttonSignUp.isClickable = buttonClick
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
                visibilityButton(View.VISIBLE, View.INVISIBLE, false)
                MainScope().launch {
                    delay(100)
                    verifyApiData()
                }
            }
        }
    }

    private fun verifyApiData() = runBlocking {
        viewModel.verifyEmailExist(
            binding.editTextEmail.text.toString(),
            {
                binding.outlinedTextFieldEmail.error = getString(R.string.input_email_exist)
                visibilityButton(View.INVISIBLE, View.VISIBLE, true)
            },
            {
                sendingApiData()
                modalSuccessCreateAccount()
            }
        )
    }

    private fun sendingApiData() = runBlocking {
        viewModel.createAccount(
            binding.editTextName.text.toString(),
            binding.editTextEmail.text.toString(),
            binding.editTextPassword.text.toString(),
            binding.autoCompleteCharacterGender.text.toString()
        )
    }

    private fun validateFieldsOnClick(editText: TextInputEditText) {
        viewModel.verifyField(editText,
            {
                viewModel.validateEmailFormat(editText.text.toString(),
                    {
                        visibilityButton(View.INVISIBLE, View.VISIBLE, true)
                        binding.outlinedTextFieldEmail.error = getString(R.string.input_error_email)
                        lastValidationField[0] = false
                    }, { lastValidationField[0] = true }
                )
            },
            {
                viewModel.validateAmountCharacters(editText.text.toString(),
                    {
                        visibilityButton(View.INVISIBLE, View.VISIBLE, true)
                        binding.outlinedTextFieldPassword.error =
                            getString(R.string.input_error_password)
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

    private fun modalSuccessCreateAccount() {
        val build = AlertDialog.Builder(this, R.style.ThemeCustomDialog)
        val dialogBinding: ModalSuccessBinding = ModalSuccessBinding
            .inflate(LayoutInflater.from(this))

        val intent = Intent(this, LoginActivity::class.java)
        dialogBinding.buttonClose.setOnClickListener { dialog.dismiss(); startActivity(intent) }
        build.setView(dialogBinding.root)
        dialog = build.create()
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
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