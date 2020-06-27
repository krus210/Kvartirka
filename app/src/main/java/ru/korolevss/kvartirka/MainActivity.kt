package ru.korolevss.kvartirka

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import ru.korolevss.kvartirka.repository.Repository
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object {
        private const val REQUEST_LOCATION = 100
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestedGeo()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestedGeo() {
        val locationPermission: String = Manifest.permission.ACCESS_COARSE_LOCATION
        val hasPermission = checkSelfPermission(locationPermission)
        val permissions = arrayOf(locationPermission)
        if (hasPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(permissions, REQUEST_LOCATION)
        } else {
            loadData(true)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_LOCATION -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadData(true)
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.selected_city_moscow), Toast.LENGTH_SHORT
                    ).show()
                    loadData(false)
                }
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun loadData(isGeoPermissionGranted: Boolean) {
        var longitude= 37.6082298810962
        var latitude = 55.7625506743728
        if (isGeoPermissionGranted) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                        if (location != null) {
                            longitude = location.longitude
                            latitude = location.latitude
                        }
                }

        }
        lifecycleScope.launch {
            try {
                val result = Repository.getPosts(longitude, latitude)
                if (result.isSuccessful) {
                    Toast.makeText(
                        this@MainActivity,
                        "OK OK OK",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        R.string.loading_posts_failed,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: IOException) {
                Toast.makeText(
                    this@MainActivity,
                    R.string.connect_to_server_failed,
                    Toast.LENGTH_SHORT
                ).show()
                Log.i("LOG", "$e")
            }
        }
    }

}