package s8u.studies.myapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import s8u.studies.myapplication.R
import s8u.studies.myapplication.api.pokemonDescriptionEndpoint
import s8u.studies.myapplication.api.pokemonEndpoint
import s8u.studies.myapplication.di.retrofitObject
import s8u.studies.myapplication.viewModel.DescriptionViewModel

class DescriptionActivity : AppCompatActivity() {
    private val viewModel = DescriptionViewModel()
    private val pokeImg: ImageView get() = findViewById(R.id.imageView)
    private val pokeName: TextView get() = findViewById(R.id.textView_name_pokemon)
    private val pokeHeight: TextView get() = findViewById(R.id.textView_height)
    private val pokeWeight: TextView get() = findViewById(R.id.textView_weight)
    private val pokeDesc: TextView get() = findViewById(R.id.textView_description)
    private val pokeType: TextView get() = findViewById(R.id.textView_type1_pokemon)
    private val pokeType2: TextView get() = findViewById(R.id.textView_type2_pokemon)
    private val pokeType3: TextView get() = findViewById(R.id.textView_type3_pokemon)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

       viewModel.GetPokemonsDescription(intent.getStringExtra("id"))

        viewModel.apiData.observe(this){
            substitute()
        }
    }


    fun substitute(){
        val api = viewModel.apiData.value

        pokeName.text = api!!.name
        pokeHeight.text = api!!.height
        pokeWeight.text = api!!.weight
        pokeDesc.text = api!!.descriptionList[0].descricao

        if(api.typeList.size > 0){
            pokeType.text = api.typeList[0].type.name
        }
        else if(api.typeList.size > 1){
            pokeType.text = api.typeList[0].type.name
            pokeType2.text = api.typeList[1].type.name
        }
        else{
            pokeType.text = api.typeList[0].type.name
            pokeType2.text = api.typeList[1].type.name
            pokeType3.text = api.typeList[2].type.name
        }
    }
}