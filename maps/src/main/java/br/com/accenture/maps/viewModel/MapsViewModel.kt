package br.com.accenture.maps.viewModel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.core.graphics.scale
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import java.net.URL
import kotlin.random.Random

class MapsViewModel() : ViewModel() {

    var pokemonPopulation = 0

    fun randomNearLocation(location: LatLng,randomGenerator:Random):LatLng {

        var latitude = 0.0
        var longitude = 0.0

        if (randomGenerator.nextBoolean()) {
            latitude = location.latitude - randomGenerator.nextDouble(0.0005, 0.001)
        } else {
            latitude = location.latitude + randomGenerator.nextDouble(0.0005, 0.001)
        }

        if (randomGenerator.nextBoolean()) {
            longitude = location.longitude - randomGenerator.nextDouble(0.0005, 0.001)
        } else {
            longitude = location.longitude + randomGenerator.nextDouble(0.0005, 0.001)
        }

        return LatLng(latitude, longitude)
    }

    fun randomPokemon(randomGenerator: Random):Int {
        return randomGenerator.nextInt(1, 150)
    }

     fun bpmConvertor(url: String): Bitmap {
        val urla = URL(url)

        var img = BitmapFactory.decodeStream(urla.openConnection().getInputStream())
            .scale(300, 300, false)

        return img
    }
}

