package br.com.accenture.maps

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.os.Bundle
import android.os.StrictMode
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.graphics.scale
import br.com.accenture.maps.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import java.net.URL
import java.util.*
import kotlin.concurrent.schedule
import kotlin.random.Random

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var currentLocation: Location

    private var pokemonPopulation = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapsInitializer.initialize(this, MapsInitializer.Renderer.LATEST) {
            println(it.name)
        }
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isCompassEnabled = false
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap.setMaxZoomPreference(20f)
        mMap.setMinZoomPreference(17f)
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.custom_map))
        setUpMap()
    }

    @SuppressLint("MissingPermission")
    private fun setUpMap() {

        val task = fusedLocationProviderClient.lastLocation

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
            return
        }
        mMap.isMyLocationEnabled = true
        task.addOnSuccessListener(this) {
            if (it != null) {
                currentLocation = it
                val currentLatLong = LatLng(it.latitude, it.longitude)
                mMap.addMarker(
                    MarkerOptions()
                        .position(currentLatLong)
                        .title("My Position")
                )!!
                    .setIcon(
                        BitmapDescriptorFactory.fromResource(R.drawable.red)
                    )
                mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLatLong))


                mMap.setOnMapLoadedCallback {
                    val cameraPosition = CameraPosition.builder()
                        .target(currentLatLong)
                        .zoom(18.5f)
                        .bearing(270f)
                        .tilt(70f)
                        .build()

                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
                }

                Timer().schedule(15000, 10000) {
                    spawnPokemons(currentLatLong)
                }
            }
        }
    }

    private fun spawnPokemons(location: LatLng) {

        var latitude = 0.0
        var longitude = 0.0

        var randomGenerator = Random(System.currentTimeMillis())

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

        val randomPokemon = randomGenerator.nextInt(1, 150)

        var nextLocation = LatLng(latitude, longitude)

        runOnUiThread {
            mMap.addMarker(
                MarkerOptions()
                    .position(nextLocation)
            )!!
                .setIcon(
                    BitmapDescriptorFactory.fromBitmap(
                        bpmConvertor("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${randomPokemon}.png")
                    )
                )

            pokemonPopulation++

            if (pokemonPopulation >= 4) {
                mMap.clear()
                mMap.addMarker(
                    MarkerOptions()
                        .position(LatLng(location.latitude,location.longitude))
                        .title("My Position")
                )!!
                    .setIcon(
                        BitmapDescriptorFactory.fromResource(R.drawable.red)
                    )
                pokemonPopulation = 0
            }

        }
    }

    private fun bpmConvertor(url: String): Bitmap {
        val urla: URL = URL(url)

        var img = BitmapFactory.decodeStream(urla.openConnection().getInputStream()).scale(300,300,false)

        return img
    }
}