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
import br.com.accenture.maps.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import java.net.URL
import kotlin.random.Random

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var currentLocation: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                spawnPokemons(currentLatLong)
            }
        }
    }

    private fun spawnPokemons(location: LatLng) {

        var latitude = 0.0
        var longitude = 0.0

        var randomGenerator = Random(System.currentTimeMillis())

        latitude = if (randomGenerator.nextBoolean()) {
            location.latitude - 0.0007
        } else {
            location.latitude + 0.0007
        }

        longitude = if (randomGenerator.nextBoolean()) {
            location.longitude - 0.0007
        } else {
            location.longitude + 0.0007
        }

        val randomPokemon = randomGenerator.nextInt(1, 650)

        val nextLocation = LatLng(latitude, longitude)

        mMap.addMarker(
            MarkerOptions()
                .position(nextLocation)
                .title("Random Pokemon")
        )!!
            .setIcon(
                BitmapDescriptorFactory.fromBitmap(
                    bpmConvertor("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${randomPokemon}.png"))
            )
    }

    private fun bpmConvertor(url: String): Bitmap {
        val urla: URL = URL(url)
        return BitmapFactory.decodeStream(urla.openConnection().getInputStream())
    }
}