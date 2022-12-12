package s8u.studies.myapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import s8u.studies.myapplication.R
import s8u.studies.myapplication.viewModel.DescriptionViewModel

class DescriptionActivity : AppCompatActivity() {
    private lateinit var viewModel: DescriptionViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
    }

}