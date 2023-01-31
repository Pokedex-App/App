package s8u.studies.login.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import s8u.studies.login.R
import s8u.studies.login.databinding.ActivitySignUpBinding
import s8u.studies.login.viewModel.SignUpViewModel

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: SignUpViewModel by viewModel()
    private var buttonVerifies = arrayListOf(false, false, false, false)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        autoCompleteConfig()
        validateFields()
        onClick()
    }

    private fun onClick() {
        binding.textViewHasAccount.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
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

    private fun validateFields() {
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
                        },
                        {
                            buttonVerifies[0] = false; buttonState()
                            binding.outlinedTextFieldName.isHintEnabled = true
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
                        },
                        {
                            buttonVerifies[1] = false; buttonState()
                            binding.outlinedTextFieldCharacterGender.isHintEnabled = true
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
                        },
                        {
                            buttonVerifies[2] = false; buttonState()
                            binding.outlinedTextFieldEmail.isHintEnabled = true
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
                        },
                        {
                            buttonVerifies[3] = false; buttonState()
                            binding.outlinedTextFieldPassword.isHintEnabled = true
                        }
                    )
                }
            }
        )
    }

    fun buttonState() {
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
}