package com.example.matchball.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.matchball.R
import com.example.matchball.createrequest.RequestActivity
import com.example.matchball.databinding.ActivityMainBinding
import com.example.matchball.homedashboard.matchlist.MatchListFragment
import com.example.matchball.firebaseconnection.AuthConnection.authUser
import com.example.matchball.map.MapsActivity
import com.example.matchball.mymatches.MyMatchesFragment
import com.example.matchball.usersetting.useroverview.UserFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*

/* Sửa UI :
 5/ activity_user_account : avatar
 6/ activity_user_info : avatar
 7/ fragment_user */

// khi đăng nhập bằng account mới thì vẫn load lại info account cũ
// check ảnh khi tạo user
// get ảnh của current User

//------------------------------

// Sửa các function deprecated : Map, UserInfoActivity
// Search
// Notification

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private val mainActivityViewModel : MainActivityViewModel by viewModels()
    private var name : String? = null
    private var phone : String? = null
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val permissionId = 2
    private lateinit var mapsActivity: MapsActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mapsActivity = MapsActivity()
        initEvents()
        initObserve()
        initUI()
        mainActivityViewModel.handleReadUserData()

    }

    private fun initEvents() {
        selectBottomNavigation()
        getLocation()
    }

    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        val geocoder = Geocoder(this, Locale.getDefault())
                        val list: List<Address> =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        val latitude = list[0].latitude
                        val longitude = list[0].longitude
                        Log.e("Latitude", latitude.toString())
                        mapsActivity.setLocation(latitude, longitude)
                    }
                }
            } else {
                Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled (
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == permissionId) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        }
    }

    private fun initObserve() {
        mainActivityViewModel.userInfo.observe(this) { result ->
            when (result) {
                is MainActivityViewModel.UserData.LoadDataOk -> {
                    name = result.name
                    phone = authUser?.phoneNumber
                }
                else -> {
                }
            }
        }
    }

    private fun initUI() {
        mainBinding.bottomNavigationView.background = null
        mainBinding.bottomNavigationView.menu.getItem(2).isEnabled = true
        loadFragment(MatchListFragment.newInstance())
    }

    private fun selectBottomNavigation() {
        mainBinding.bottomNavigationView.setOnItemSelectedListener { item ->
            val fragment : Fragment
            when (item.itemId) {
                R.id.home -> {
                    fragment = MatchListFragment()
                    loadFragment(fragment)
                    true
                }
                R.id.person -> {
                    fragment = UserFragment()
                    loadFragment(fragment)
                    true
                }
                R.id.my_matches -> {
                    fragment = MyMatchesFragment()
                    loadFragment(fragment)
                    true
                }
                R.id.pitch -> {
//                    fragment = MapsFragment()
//                    loadFragment(fragment)
                    startActivity(Intent(applicationContext, MapsActivity::class.java))
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.request -> {
                    if (name != null && phone != null) {
                        startActivity(Intent(applicationContext, RequestActivity::class.java))
                        overridePendingTransition(0, 0)
                    } else {
                        Toast.makeText(this, "Please complete your Profile", Toast.LENGTH_SHORT).show()
                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

// Print KeyHash Syntax
//
//    private fun printKeyHash() {
//        try {
//            val info : PackageInfo = this.packageManager.getPackageInfo(this.packageName, PackageManager.GET_SIGNATURES)
//            for (signature in info.signatures) {
//                val md : MessageDigest = MessageDigest.getInstance("SHA")
//                md.update(signature.toByteArray())
//                val hashKey = String(Base64.encode(md.digest(), 0))
//                Log.d(TAG, "printHaskKey() Hask Key: $hashKey")
//            }
//        } catch (e : NoSuchAlgorithmException) {
//            Log.d(TAG, "printHashKey()", e)
//        } catch (e: Exception) {
//            Log.d(TAG, "printHaskKey", e)
//        }
//    }

}