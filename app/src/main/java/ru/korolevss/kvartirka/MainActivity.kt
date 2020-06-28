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
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import ru.korolevss.kvartirka.flatadapter.FlatAdapter
import ru.korolevss.kvartirka.flatadapter.FlatDiffUtilCallback
import ru.korolevss.kvartirka.model.Flat
import ru.korolevss.kvartirka.repository.Repository
import java.io.IOException


class MainActivity : AppCompatActivity(), FlatAdapter.OnLoadMoreBtnClickListener {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object {
        private const val REQUEST_LOCATION = 100
        private var offset: Int = 0
        private var longitude = 37.6082298810962
        private var latitude = 55.7625506743728
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
        if (isGeoPermissionGranted) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        longitude = location.longitude
                        latitude = location.latitude
                    }
                }

        }

        swipeContainer.setOnRefreshListener {
            refreshData()
        }

        lifecycleScope.launch {
            try {
                determinateBar.isVisible = true
                val result = Repository.getPosts(longitude, latitude, 0)
                if (result.isSuccessful) {
                    val flats = (result.body()?.flats ?: emptyList()) as MutableList<Flat>
                    with(container) {
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = FlatAdapter(flats).apply {
                            loadMoreBtnClickListener = this@MainActivity
                        }
                    }
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
            } finally {
                determinateBar.isVisible = false
            }
        }
    }

    private fun refreshData() {
        lifecycleScope.launch {
            try {
                val newData = Repository.getPosts(longitude, latitude, 0)
                swipeContainer.isRefreshing = false
                if (newData.isSuccessful) {
                    with(container) {
                        val oldList = (adapter as FlatAdapter).list
                        val newList = newData.body()!!.flats as MutableList<Flat>
                        val flatDiffUtilCallback = FlatDiffUtilCallback(oldList, newList)
                        val flatDiffResult = DiffUtil.calculateDiff(flatDiffUtilCallback)
                        (adapter as FlatAdapter).newPosts(newList)
                        flatDiffResult.dispatchUpdatesTo(adapter as FlatAdapter)
                    }
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        R.string.loading_posts_failed,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: IOException) {
                swipeContainer.isRefreshing = false
                Toast.makeText(
                    this@MainActivity,
                    R.string.connect_to_server_failed,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onLoadMoreBtnClicked() {
        lifecycleScope.launch {
            try {
                offset += 41
                determinateBar.isVisible = true
                val result = Repository.getPosts(longitude, latitude, offset)
                if (result.isSuccessful) {
                    val newFlats =
                        (result.body()?.flats ?: emptyList()) as MutableList<Flat>
                    with(container.adapter as FlatAdapter) {
                        val lastIndexOfOldList = list.lastIndex
                        list.addAll(newFlats)
                        notifyItemRangeInserted(lastIndexOfOldList + 1, newFlats.size)
                    }
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
            } finally {
                determinateBar.isVisible = false
            }
        }
    }
}