package paba.meet2.uts.c14230232.meet10

import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import paba.meet2.uts.c14230232.meet10.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding : ActivityMainBinding? = null
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(R.layout.activity_main)
        setContentView(binding!!.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding!!.toolBar) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val params = v.layoutParams as ViewGroup.MarginLayoutParams
            params.topMargin = systemBars.top //Biar tidak ketutup jam dan bar batterai hp
            v.layoutParams = params
            insets
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding!!.navView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding?.apply {
            setSupportActionBar(toolBar)
            navView.bringToFront()
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.frameLayout) as NavHostFragment
        navController = navHostFragment.navController
        binding!!.navView.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            navController.graph,
            binding!!.drawer
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}