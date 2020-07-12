package com.felixfavour.pidgipedia

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.felixfavour.pidgipedia.util.Pidgipedia.SOURCE
import com.felixfavour.pidgipedia.util.snack
import com.felixfavour.pidgipedia.util.toast
import com.felixfavour.pidgipedia.viewmodel.MainActivityViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.Source
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home,
            R.id.navigation_dictionary,
            R.id.navigation_quiz
        ))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        checkConnectivity()
        return super.onCreateView(name, context, attrs)
    }


    override fun onRestart() {
        super.onRestart()
        checkConnectivity()
    }


    override fun onResume() {
        super.onResume()
        checkConnectivity()
    }

    private fun checkConnectivity() {
        try {
            val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val request = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                connectivityManager.requestNetwork(request, object: ConnectivityManager.NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        super.onAvailable(network)
                        SOURCE = Source.DEFAULT
                    }

                    override fun onUnavailable() {
                        super.onUnavailable()
                        SOURCE = Source.CACHE
                        findViewById<View>(R.id.main_activity_layout).apply {
                            if (this != null)
                                snack(this, context.getString(R.string.no_internet_access))
                        }
                    }

                    override fun onLost(network: Network) {
                        super.onLost(network)
                        SOURCE = Source.CACHE
                        findViewById<View>(R.id.main_activity_layout).apply {
                            if (this != null)
                                snack(this, context.getString(R.string.internet_unstable))
                        }
                    }
                }, 5000)
            } else {
                connectivityManager.requestNetwork(request, object: ConnectivityManager.NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        super.onAvailable(network)
                        SOURCE = Source.DEFAULT
                    }

                    override fun onLost(network: Network) {
                        super.onLost(network)
                        SOURCE = Source.CACHE
                        findViewById<View>(R.id.main_activity_layout).apply {
                            if (this != null)
                                snack(this, context.getString(R.string.internet_unstable))
                        }
                    }
                })
            }
        } catch (ex: Exception) {}
    }
}
