package s8u.studies.myapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import s8u.studies.myapplication.R
import s8u.studies.myapplication.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val regions = resources.getStringArray(R.array.list_regions)
        val adapter = ArrayAdapter(this, R.layout.list_item, regions)
        binding.inputAutocomplete.setAdapter(adapter)
    }
}