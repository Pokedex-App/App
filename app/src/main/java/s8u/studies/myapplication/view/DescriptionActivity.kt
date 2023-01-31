package s8u.studies.myapplication.view

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import org.koin.androidx.viewmodel.ext.android.viewModel
import s8u.studies.myapplication.R
import s8u.studies.myapplication.databinding.ActivityDescriptionBinding
import s8u.studies.myapplication.databinding.ModalPokemonBinding
import s8u.studies.myapplication.model.ColorBackgroundType
import s8u.studies.myapplication.model.Pokemon.PokedexTypes
import s8u.studies.myapplication.model.Pokemon.abilities.PokemonMoves
import s8u.studies.myapplication.model.PokemonData
import s8u.studies.myapplication.recyclerview.adapter.ListAbilitiesAdapter
import s8u.studies.myapplication.viewModel.DescriptionViewModel

class DescriptionActivity : AppCompatActivity(), ListAbilitiesAdapter.OnListenerAbility {
    private lateinit var binding: ActivityDescriptionBinding
    private val viewModel: DescriptionViewModel by viewModel()
    private val toolbar: androidx.appcompat.widget.Toolbar get() = findViewById(R.id.toolbar_description)
    private val nextButton: Button get() = findViewById(R.id.button_next)
    private val previousButton: Button get() = findViewById(R.id.button_previous)
    private lateinit var idPokemon: String
    private lateinit var firstPokemon: String
    private lateinit var lastPokemon: String
    private lateinit var orderList: ArrayList<PokedexTypes>
    private var position: Int = 0
    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        idPokemon = intent.getStringExtra("id").toString()
        firstPokemon = intent.getStringExtra("firstPokemon").toString()
        lastPokemon = intent.getStringExtra("lastPokemon").toString()
        val b = intent.getBundleExtra("listOrder")
        orderList = b!!.getSerializable("listOrder") as ArrayList<PokedexTypes>
        position = intent.getIntExtra("position", 0)
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
            onBackPressed()
        }
        nextButton.setOnClickListener {
            nextPokemon()
            isClickableButtonToolbar(false)
        }
        previousButton.setOnClickListener {
            previousPokemon()
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
        binding.textViewHeight.text =
            Html.fromHtml(
                "<b>Height</b> ${(api.height.toDouble() / 10)} m",
                Html.FROM_HTML_MODE_LEGACY
            )
        binding.textViewWeight.text =
            Html.fromHtml(
                "<b>Weight</b> ${(api.weight.toDouble() / 10)} kg",
                Html.FROM_HTML_MODE_LEGACY
            )
        binding.textViewDescription.text = api.descriptionList[0].description.replace("\n", " ")
        binding.imageView.load(api.imgList.imgList.type.urlImg)

        val primaryType = api.typeList[0].type.name
        binding.textViewPrimaryTypePokemon.text = primaryType
        binding.textViewPrimaryTypePokemon.backgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(
                this,
                ColorBackgroundType.TYPE.getColor(primaryType)
            )
        )
        binding.textViewSecondaryTypePokemon.visibility =
            viewModel.visibilitySecondaryType(api.typeList.size)
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

        abilityItemObjects(api)
    }

    private fun abilityItemObjects(api: PokemonData) {
        val listAbilities = api.movesList
        val gridLayoutManager = GridLayoutManager(this, 2)
        binding.recyclerViewAbilities.layoutManager = gridLayoutManager
        binding.recyclerViewAbilities.adapter = ListAbilitiesAdapter(
            this, listAbilities, this
        )
    }

    override fun onClickAbility(ability: PokemonMoves) {
        val dialogBinding: ModalPokemonBinding =
            ModalPokemonBinding.inflate(LayoutInflater.from(this))
        showDialogAbility(dialogBinding, ability)
        visibilityModal(dialogBinding, View.VISIBLE, View.INVISIBLE)
    }

    private fun showDialogAbility(dialogBinding: ModalPokemonBinding, ability: PokemonMoves) {
        val build = AlertDialog.Builder(this, R.style.ThemeCustomDialog)

        dialogBinding.buttonClose.setOnClickListener { dialog.dismiss() }
        dialogBinding.buttonClose.isClickable = false
        dialogBinding.textViewTitleNameAbility.text = ability.move.moveName
        build.setView(dialogBinding.root)
        dialog = build.create()
        dialog.show()

        viewModel.getAbilityInformation(ability.move.moveName)
        viewModel.abilityInformationPokemon.observe(this) {
            if (it.power == null) it.power = 0
            dialogBinding.textViewDescriptionAbility.text =
                it.flavorTextEntries[0].flavorText.replace("\n", " ")
            dialogBinding.textViewPowerAbility.text = it.power.toString()
            dialogBinding.textViewTypeAbility.text = it.type.nameType
            dialogBinding.textViewDamageAbility.text = it.damage.nameDamage
            dialogBinding.buttonClose.isClickable = true
            visibilityModal(dialogBinding, View.INVISIBLE, View.VISIBLE)
        }
    }

    private fun visibilityModal(
        dialogBinding: ModalPokemonBinding,
        loading: Int,
        information: Int
    ) {
        dialogBinding.textViewLabelType.visibility = information
        dialogBinding.textViewTypeAbility.visibility = information
        dialogBinding.textViewLabelPower.visibility = information
        dialogBinding.textViewPowerAbility.visibility = information
        dialogBinding.textViewLabelDamage.visibility = information
        dialogBinding.textViewDamageAbility.visibility = information
        dialogBinding.textViewDescriptionAbility.visibility = information
        dialogBinding.progressBar.visibility = loading
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
        binding.loadingText.visibility = loading
        binding.loading.visibility = loading
    }

    private fun isClickableButtonToolbar(state: Boolean) {
        findViewById<Button>(R.id.button_next).isClickable = state
        findViewById<Button>(R.id.button_previous).isClickable = state
    }

    private fun nextPokemon() {
        position++
        viewModel.getPokemonDescription(orderList[position].id, firstPokemon, lastPokemon)
    }

    private fun previousPokemon() {
        position--
        viewModel.getPokemonDescription(orderList[position].id, firstPokemon, lastPokemon)
    }
}