package br.com.accenture.maps.viewModel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.graphics.scale
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import java.net.URL
import kotlin.random.Random

class MapsViewModel() : ViewModel() {

    var pokemonPopulation = 0

    fun randomNearLocation(location: LatLng,randomGenerator:Random):LatLng {
        var latitude = if (randomGenerator.nextBoolean()) {
            location.latitude - randomGenerator.nextDouble(0.0005, 0.001)
        } else {
            location.latitude + randomGenerator.nextDouble(0.0005, 0.001)
        }

        var longitude = if (randomGenerator.nextBoolean()) {
            location.longitude - randomGenerator.nextDouble(0.0005, 0.001)
        } else {
            location.longitude + randomGenerator.nextDouble(0.0005, 0.001)
        }

        return LatLng(latitude, longitude)
    }

    fun randomPokemon(randomGenerator: Random):Int {
        return randomGenerator.nextInt(1, 150)
    }

    fun bpmConvertor(url: String): Bitmap {
        val urla = URL(url)

        return BitmapFactory.decodeStream(urla.openConnection().getInputStream())
            .scale(300, 300, false)
    }

    fun verifyPermission(permissionFine: Int, permissionCoarse: Int, permissionGranted: Int, behavior: () -> Unit) {
        if (permissionFine != permissionGranted && permissionCoarse != permissionGranted) behavior()
    }

    fun verifyLocation(location: Location, behavior: () -> Unit) {
        if (location != null) behavior()
    }

    fun verifyPopulation(size: Int, behavior: () -> Unit, elseBehavior: () -> Unit) {
        if (size > 3) behavior()
        else elseBehavior()
    }

    fun wasClicked(isClicked: Boolean, behavior: () -> Unit, elseBehavior: () -> Unit) {
        if (isClicked) behavior()
        else elseBehavior()
    }
}

