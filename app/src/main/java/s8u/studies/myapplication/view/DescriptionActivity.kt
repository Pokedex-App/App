package s8u.studies.myapplication.view

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import coil.load
import org.koin.core.context.stopKoin
import s8u.studies.myapplication.R
import s8u.studies.myapplication.databinding.ActivityDescriptionBinding
import s8u.studies.myapplication.model.ColorBackgroundType
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
        setObserversLoading()
    }

    private fun setObserversLoading() {
        viewModel.setLoadingState(true)
        viewModel.loadingPokeballTrue.observe(this) {
            visibilityLayout(View.VISIBLE, View.INVISIBLE)
        }
        viewModel.loadingPokeballFalse.observe(this) {
            visibilityLayout(View.INVISIBLE, View.VISIBLE)
        }
    }

    private fun onClick() {
        toolbar.setNavigationOnClickListener {
            stopKoin()
            onBackPressed()
        }
        nextButton.setOnClickListener {
            nextPokemon(viewModel.apiData.value!!.id)
            isClickableButtonToolbar(false)
        }
        previousButton.setOnClickListener {
            previousPokemon(viewModel.apiData.value!!.id)
            isClickableButtonToolbar(false)
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
            viewModel.setLoadingState(false)
            isClickableButtonToolbar(true)
            printOnScreenInformation()
        }
    }

    private fun printOnScreenInformation() {
        val api = viewModel.apiData.value

        binding.textViewNamePokemon.text = api!!.name
        binding.textViewHeight.text = Html.fromHtml("<b>Height</b> ${(api.height.toDouble() / 10)} m")
        binding.textViewWeight.text = Html.fromHtml("<b>Weight</b> ${(api.weight.toDouble() / 10)} kg")
        binding.textViewDescription.text = api.descriptionList[0].descricao
        binding.imageView.load(api.imgList.imgList.type.urlImg)

        val primaryType = api.typeList[0].type.name
        binding.textViewPrimaryTypePokemon.text = primaryType
        binding.textViewPrimaryTypePokemon.backgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(
                this,
                ColorBackgroundType.TYPE.getColor(primaryType)
            )
        )
        binding.textViewSecondaryTypePokemon.visibility = viewModel.visibilitySecondaryType(api.typeList.size)
        viewModel.existsSecondaryType(api.typeList.size) {
            val secondaryType = api.typeList[1].type.name
            binding.textViewSecondaryTypePokemon.text = secondaryType
            binding.textViewSecondaryTypePokemon.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    this,
                    ColorBackgroundType.TYPE.getColor(secondaryType)
                )
            )
        }

        binding.textViewAbility1.text = api.movesList[0].move.moveName
        binding.textViewAbility2.text = api.movesList[1].move.moveName
    }

    private fun visibilityLayout(loading: Int, information: Int) {
        binding.toolbarDescription.toolbar.visibility = information
        binding.imageView.visibility = information
        binding.textViewNamePokemon.visibility = information
        binding.viewTypes.visibility = information
        binding.viewHeightWeight.visibility = information
        binding.textViewDescription.visibility = information
        binding.textViewTitleAbilities.visibility = information
        binding.viewAbilities.visibility = information
        binding.buttonSeeMore.visibility = information
        binding.loadingText.visibility = loading
        binding.loading.visibility = loading
    }

    private fun isClickableButtonToolbar(state: Boolean) {
        findViewById<Button>(R.id.button_next).isClickable = state
        findViewById<Button>(R.id.button_previous).isClickable = state
    }

    private fun nextPokemon(id: Int) {
        viewModel.getPokemonDescription((id + 1).toString(),firstPokemon,lastPokemon)
    }

    private fun previousPokemon(id: Int) {
        viewModel.getPokemonDescription((id - 1).toString(),firstPokemon,lastPokemon)
    }
}