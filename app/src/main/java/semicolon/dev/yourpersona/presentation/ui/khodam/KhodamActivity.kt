package semicolon.dev.yourpersona.presentation.ui.khodam

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import semicolon.dev.yourpersona.R
import semicolon.dev.yourpersona.databinding.ActivityKhodamBinding

@AndroidEntryPoint
class KhodamActivity : AppCompatActivity() {

    private val binding by lazy { ActivityKhodamBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navControl = navHostFragment.navController

        navControl.navigate(R.id.navigation_cek_khodam)
        binding.navViewHome.setupWithNavController(navControl)
    }
}