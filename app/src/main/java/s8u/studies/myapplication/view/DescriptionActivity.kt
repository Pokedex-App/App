package s8u.studies.myapplication.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import coil.load
import org.koin.core.context.stopKoin
import s8u.studies.myapplication.R
import s8u.studies.myapplication.viewModel.DescriptionViewModel

class DescriptionActivity : AppCompatActivity() {
    private val viewModel = DescriptionViewModel()
    private val toolbar: androidx.appcompat.widget.Toolbar get() = findViewById(R.id.toolbar_description)
    private val nextButton: Button get() = findViewById(R.id.button_next)
    private val previousButton: Button get() = findViewById(R.id.button_previous)
    private val pokeImg: ImageView get() = findViewById(R.id.imageView)
    private val pokeName: TextView get() = findViewById(R.id.textView_name_pokemon)
    private val pokeHeight: TextView get() = findViewById(R.id.textView_height)
    private val pokeWeight: TextView get() = findViewById(R.id.textView_weight)
    private val pokeDesc: TextView get() = findViewById(R.id.textView_description)
    private val pokeTypePrimary: TextView get() = findViewById(R.id.textView_primary_type_pokemon)
    private val pokeTypeSecondary: TextView get() = findViewById(R.id.textView_secondary_type_pokemon)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        onClick()
        viewModel.getPokemonDescription(intent.getStringExtra("id").toString())
        viewModel.apiData.observe(this) {
            printOnScreenInformation()
        }
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

        pokeName.text = api!!.name
        pokeHeight.text = "Height " + api.height
        pokeWeight.text = "Weight " + api.weight
        pokeDesc.text = api.descriptionList[0].descricao
        pokeImg.load(api.imgList.imgList.type.urlImg)

        viewModel.changeBasedOnTypes(api.typeList)
        viewModel.pokemonLiveData1.observe(this) {
            pokeTypePrimary.text = api.typeList[0].type.name
            pokeTypeSecondary.visibility = View.GONE
        }
        viewModel.pokemonLiveData2.observe(this) {
            pokeTypePrimary.text = api.typeList[0].type.name
            pokeTypeSecondary.text = api.typeList[1].type.name
        }
    }

    private fun nextPokemon(id : Int) {
        viewModel.getPokemonDescription((id + 1).toString())
    }

    private fun previousPokemon(id : Int) {
        viewModel.getPokemonDescription((id - 1).toString())
    }
}