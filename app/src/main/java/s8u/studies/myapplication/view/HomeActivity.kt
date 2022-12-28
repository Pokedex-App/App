package s8u.studies.myapplication.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin
import s8u.studies.myapplication.R
import s8u.studies.myapplication.databinding.ActivityHomeBinding
import s8u.studies.myapplication.model.Pokedex.PokedexEntries
import s8u.studies.myapplication.model.Pokemon.PokemonTypeEnd
import s8u.studies.myapplication.modules.networkModule
import s8u.studies.myapplication.recyclerview.adapter.ListPokedexAdapter
import s8u.studies.myapplication.viewModel.HomeViewModel

class HomeActivity : AppCompatActivity(), ListPokedexAdapter.OnListenerPokedex {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setKoinUp()
        pokedexItemObjects()
        setObserversLoading()
        setOnClick()

    }

    private fun setKoinUp() {
        startKoin {
            modules(networkModule)
        }
    }

    private fun setObserversLoading() {
        viewModel.setLoadingState(true)
        viewModel.loadingPokeballTrue.observe(this) {
            visibilityLayout(View.VISIBLE, View.INVISIBLE)
        }
        viewModel.loadingPokeballFalse.observe(this) {
            visibilityLayout(View.INVISIBLE, View.VISIBLE)
        }
        viewModel.listPokedexFilteredLiveData.observe(this) {
            val listaFiltrada = arrayListOf<PokedexEntries>()
            val listaFiltroPokedex = viewModel.listPokedexFilteredLiveData.value

            for(i in 0 until listaFiltroPokedex!!.pokedexSpecies.size) {
                listaFiltrada.add(viewModel.updateLiveData(listaFiltroPokedex.pokedexSpecies[i].pokemon,i))

                if(i == listaFiltroPokedex.pokedexSpecies.size - 1){
                    viewModel.setLiveEntries(listaFiltrada)
                    viewModel.getPokedexTypesList()
                }
            }
        }
        viewModel.listPokedexTypesLiveData.observe(this) {
            val recyclerView = findViewById<RecyclerView>(R.id.RecyclerView)
            recyclerView.adapter!!.notifyDataSetChanged()
        }
    }

    private fun visibilityLayout(loading: Int, information: Int) {
        binding.textViewTitleApp.visibility = information
        binding.textViewTitleFilter.visibility = information
        binding.scrollViewFilter.visibility = information
        binding.inputLayout.visibility = information
        binding.titleListPokemon.visibility = information
        binding.RecyclerView.visibility = information
        binding.loading.visibility = loading
    }

    private fun pokedexItemObjects() {
        val recyclerView = findViewById<RecyclerView>(R.id.RecyclerView)
        viewModel.getPokedexEntriesList()

        viewModel.listPokedexEntriesLiveData.observe(this) {
            viewModel.getPokedexTypesList()
        }

        viewModel.listPokedexTypesLiveData.observe(this) {
            val listPokedexEntries = viewModel.listPokedexEntriesLiveData.value
            val typeList = viewModel.listPokedexTypesLiveData.value
            val entriesAndTypeList = Pair(listPokedexEntries, typeList)
            recyclerView.adapter = ListPokedexAdapter(
                this, entriesAndTypeList, this
            )
            viewModel.setLoadingState(false)
        }
    }

    private fun filterByType(id:String) {
        val ListaPokedex = viewModel.listPokedexEntriesLiveData.value
        viewModel.getPokedexFilteredList(id)
        viewModel.setLoadingState(true)
    }

    private fun setOnClick() {
        val bug = findViewById<ImageView>(R.id.bugImg)
        val dark = findViewById<ImageView>(R.id.darkImg)
        val dragon = findViewById<ImageView>(R.id.dragonImg)
        val eletric = findViewById<ImageView>(R.id.eletricImg)
        val fighter = findViewById<ImageView>(R.id.fightingImg)
        val fairy = findViewById<ImageView>(R.id.fairyImg)
        val fire = findViewById<ImageView>(R.id.fireImg)
        val flying = findViewById<ImageView>(R.id.flyingImg)
        val ghost = findViewById<ImageView>(R.id.ghostImg)
        val grass = findViewById<ImageView>(R.id.grassImg)
        val ground = findViewById<ImageView>(R.id.groundImg)
        val ice = findViewById<ImageView>(R.id.iceImg)
        val normal = findViewById<ImageView>(R.id.normalImg)
        val poison = findViewById<ImageView>(R.id.poisonImg)
        val psychic = findViewById<ImageView>(R.id.psychicImg)
        val rock = findViewById<ImageView>(R.id.rockImg)
        val steel = findViewById<ImageView>(R.id.steelImg)
        val water = findViewById<ImageView>(R.id.waterImg)

        bug.setOnClickListener {
            filterByType("7")
        }
        dark.setOnClickListener {
            filterByType("17")
        }
        dragon.setOnClickListener {
            filterByType("16")
        }
        eletric.setOnClickListener {
            filterByType("13")
        }
        fighter.setOnClickListener {
            filterByType("2")
        }
        fire.setOnClickListener {
            filterByType("10")
        }
        flying.setOnClickListener {
            filterByType("3")
        }
        ghost.setOnClickListener {
            filterByType("8")
        }
        grass.setOnClickListener {
            filterByType("12")
        }
        ground.setOnClickListener {
            filterByType("5")
        }
        ice.setOnClickListener {
            filterByType("15")
        }
        normal.setOnClickListener {
            filterByType("1")
        }
        poison.setOnClickListener {
            filterByType("4")
        }
        psychic.setOnClickListener {
            filterByType("14")
        }
        rock.setOnClickListener {
            filterByType("7")
        }
        water.setOnClickListener {
            filterByType("11")
        }
        steel.setOnClickListener {
            filterByType("9")
        }
        fairy.setOnClickListener{
            filterByType("18")
        }

    }

    override fun onClickPokedex(pokedexEntries: PokedexEntries, pokedexTypes: PokemonTypeEnd) {
        val listPokedex = viewModel.listPokedexEntriesLiveData.value!!
        val typeList = viewModel.listPokedexTypesLiveData.value!!

        val intent = Intent(this, DescriptionActivity::class.java)
        intent.putExtra("id", pokedexTypes.id)
        intent.putExtra("a", typeList[0].id)
        Log.i("INTENT", "${typeList[0].id}")
        intent.putExtra("b", typeList[typeList.size - 1].id)
        Log.i("INTENT", "${typeList[typeList.size - 1].id}")

        startActivity(intent)
    }
}