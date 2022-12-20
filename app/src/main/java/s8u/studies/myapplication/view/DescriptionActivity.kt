package s8u.studies.myapplication.view

import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import coil.load
import org.koin.core.context.stopKoin
import s8u.studies.myapplication.R
import s8u.studies.myapplication.databinding.ActivityDescriptionBinding
import s8u.studies.myapplication.viewModel.DescriptionViewModel

class DescriptionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDescriptionBinding
    private val viewModel = DescriptionViewModel()
    private val toolbar: androidx.appcompat.widget.Toolbar get() = findViewById(R.id.toolbar_description)
    private val nextButton: Button get() = findViewById(R.id.button_next)
    private val previousButton: Button get() = findViewById(R.id.button_previous)
    private lateinit var idPokemon:String
    private lateinit var firstPokemon:String
    private lateinit var lastPokemon :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        idPokemon = intent.getStringExtra("id").toString()
        firstPokemon = intent.getStringExtra("a").toString()
        lastPokemon = intent.getStringExtra("b").toString()
        onClick()
        buttonVisibility(idPokemon)
    }

    private fun onClick() {
        toolbar.setNavigationOnClickListener {
            stopKoin()
            onBackPressed()
        }
        nextButton.setOnClickListener {
            nextPokemon(viewModel.apiData.value!!.id)
        }
        previousButton.setOnClickListener {
            previousPokemon(viewModel.apiData.value!!.id)
        }
    }

    private fun printOnScreenInformation() {
        val api = viewModel.apiData.value

        binding.textViewNamePokemon.text = api!!.name
        binding.textViewHeight.text = Html.fromHtml("<b>Height</b> ${((api.height.toDouble() * 10)/100 )} m")
        binding.textViewWeight.text = Html.fromHtml("<b>Weight</b> ${(api.weight.toDouble() / 10)} kg")
        binding.textViewDescription.text = api.descriptionList[0].descricao
        binding.imageView.load(api.imgList.imgList.type.urlImg)

        binding.textViewPrimaryTypePokemon.text = api.typeList[0].type.name
        binding.textViewSecondaryTypePokemon.visibility = viewModel.visibilitySecondaryType(api.typeList.size)
        viewModel.existsSecondaryType(api.typeList.size) {
            binding.textViewSecondaryTypePokemon.text = api.typeList[1].type.name
        }
    }

    private fun buttonVisibility(id: String) {
        viewModel.getPokemonDescription(id, firstPokemon, lastPokemon)
        viewModel.pokemonLiveData1.observe(this) {
            previousButton.visibility = View.GONE
        }
        viewModel.pokemonLiveData2.observe(this) {
            nextButton.visibility = View.GONE
        }
        viewModel.pokemonLiveData3.observe(this) {
            nextButton.visibility = View.VISIBLE
            previousButton.visibility = View.VISIBLE
        }
        viewModel.apiData.observe(this) {
            printOnScreenInformation()
        }
    }

    private fun nextPokemon(id: Int) {
        viewModel.getPokemonDescription((id + 1).toString(),firstPokemon,lastPokemon)
    }

    private fun previousPokemon(id: Int) {
        viewModel.getPokemonDescription((id - 1).toString(),firstPokemon,lastPokemon)
    }
}