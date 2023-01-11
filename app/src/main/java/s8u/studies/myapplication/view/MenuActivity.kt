package s8u.studies.myapplication.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import s8u.studies.myapplication.R
import s8u.studies.myapplication.databinding.ActivityMenuBinding
import s8u.studies.myapplication.viewModel.MenuViewModel

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private val viewModel = MenuViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        autoCompleteConfig()
        onClicks()
    }

    private fun onClicks() {
        binding.buttonConfirm.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("regionID",viewModel.getRegionsId(binding.inputAutocomplete.text.toString()))
            startActivity(intent)
        }
    }

    private fun autoCompleteConfig() {
        val regions = resources.getStringArray(R.array.list_regions)
        val adapter = ArrayAdapter(this, R.layout.list_item, regions)
        binding.inputAutocomplete.setAdapter(adapter)
    }
}