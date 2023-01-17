package s8u.studies.myapplication.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import s8u.studies.myapplication.R
import s8u.studies.myapplication.databinding.ActivityMenuBinding
import s8u.studies.myapplication.databinding.ModalErrorBinding
import s8u.studies.myapplication.viewModel.MenuViewModel

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private val viewModel = MenuViewModel()
    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        autoCompleteConfig()
        onClicks()
    }

    private fun onClicks() {
        var b = true
        binding.buttonConfirm.setOnClickListener {
            val nameRegion = binding.inputAutocomplete.text.toString()
            viewModel.checkContent(nameRegion, { showErrorModal() }, { goToList(nameRegion) })
        }
        binding.inputAutocomplete.setOnClickListener {
            if (b) {
                binding.imageMenu.visibility = View.INVISIBLE
                b = false
            } else {
                binding.imageMenu.visibility = View.VISIBLE
                b = true
            }
        }
// set image invisible
        binding.inputAutocomplete.setOnItemClickListener { _, _, _, _ ->
            binding.imageMenu.visibility = View.VISIBLE
            b = true
        }
    }

    private fun goToList(nameRegion: String) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("regionID", viewModel.getRegionsId(nameRegion))
        startActivity(intent)
    }

    private fun autoCompleteConfig() {
        val regions = resources.getStringArray(R.array.list_regions)
        val adapter = ArrayAdapter(this, R.layout.list_item, regions)
        binding.inputAutocomplete.setAdapter(adapter)
    }

    private fun showErrorModal() {
        val build = AlertDialog.Builder(this, R.style.ThemeCustomDialog)
        val dialogBinding: ModalErrorBinding = ModalErrorBinding
            .inflate(LayoutInflater.from(this))

        dialogBinding.textViewTitleError.text = getText(
            R.string.modal_error_title_pokemonNotFound
        )
        dialogBinding.textViewDescriptionError.text = getText(
            R.string.modal_error_description_regionNotSelected
        )
        dialogBinding.buttonClose.setOnClickListener { dialog.dismiss() }
        build.setView(dialogBinding.root)
        dialog = build.create()
        dialog.show()
    }
}