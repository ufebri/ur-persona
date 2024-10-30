@file:Suppress("DEPRECATION")

package semicolon.dev.yourpersona.presentation;

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.inappmessaging.FirebaseInAppMessaging
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import semicolon.dev.yourpersona.databinding.ActivityMainBinding
import semicolon.dev.yourpersona.model.MenuMapper
import semicolon.dev.yourpersona.model.Popup
import semicolon.dev.yourpersona.presentation.adapter.FireAdapter
import semicolon.dev.yourpersona.presentation.adapter.NotifAdapter
import semicolon.dev.yourpersona.presentation.ui.detail.DetailActivity
import semicolon.dev.yourpersona.presentation.ui.khodam.KhodamActivity
import semicolon.dev.yourpersona.util.copyToClipboard
import semicolon.dev.yourpersona.util.datastore.DataStoreHelper
import semicolon.dev.yourpersona.util.sendNotification
import semicolon.dev.yourpersona.util.showFiamDialog
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    //private lateinit var webSocketManager: WebSocketManager
    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var firebaseInAppMessaging: FirebaseInAppMessaging

    @Inject
    lateinit var firebaseMessaging: FirebaseMessaging

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        askNotificationPermission()

        firebaseInAppMessaging.isAutomaticDataCollectionEnabled = true

//        // URL WebSocket (gunakan IP lokal yang sesuai)
//        val serverUrl = ""
//
//        // Inisialisasi WebSocketManager
//        webSocketManager = WebSocketManager(serverUrl)
//
//        // Hubungkan ke server WebSocket
//        webSocketManager.connect()

        binding.apply {

            showLoading(true)

            firebaseMessaging.token.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    viewModel.insertFire(task.result)
                }
            }

            FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    viewModel.insertFire(String.format("ini %s", task.result.token))
                }
            }

            rvListUser.apply {
                layoutManager =
                    LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
//                val menuAdapter = MenuAdapter {
//                    goToPage(it.idMenu)
//                }
//                adapter = menuAdapter
//                menuAdapter.submitList(MenuMapper.generateMenuList)

                val fireAdapter = FireAdapter { it.firebaseID?.copyToClipboard(this@MainActivity) }
                adapter = fireAdapter

                viewModel.listFire.observe(this@MainActivity) {
                    if (it != null) {
                        fireAdapter.submitList(it)
                        rvListUser.isVisible = true
                        btnSendNotif.isVisible = true
                        tvBody.isGone = true
                    } else {
                        tvBody.isVisible = true
                        rvListUser.isGone = true
                        btnSendNotif.isGone = true
                    }
                    showLoading(false)
                }
            }

            runBlocking {
                DataStoreHelper.apply {
                    if (getList<Popup>(this@MainActivity, "contentList").isEmpty()) {
                        Toast.makeText(this@MainActivity, "Kosong Bannernya", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        showBanner(getList<Popup>(this@MainActivity, "contentList").first())
                    }

                    rvNotif.apply {
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        val notifAdapter = NotifAdapter {
                            startActivity(
                                Intent(
                                    this@MainActivity,
                                    DetailActivity::class.java
                                ).putExtra("detail_data", it).putExtra("key", "promo")
                            )
                        }
                        adapter = notifAdapter
                        val mList = getList<Popup>(this@MainActivity, "contentPromo")
                        notifAdapter.submitList(mList)
                    }
                }
            }

            btnSendNotif.setOnClickListener { sendNotification("bisa", "suara") }
        }
    }

    private fun showBanner(popup: Popup?) {
        showFiamDialog(popup, onClick = {
            startActivity(
                Intent(
                    this@MainActivity,
                    DetailActivity::class.java
                ).putExtra("detail_data", it).putExtra("key", "program")
            )
        })
    }

    private fun goToPage(id: Int) {
        when (id) {
            MenuMapper.CEK_KHODAM -> startActivity(
                Intent(
                    this@MainActivity,
                    KhodamActivity::class.java
                )
            )
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Notifications permission granted", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this, "Notifications permission rejected", Toast.LENGTH_SHORT).show()
            }
        }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun showLoading(isShowing: Boolean) {
        binding.progress.isVisible = isShowing
    }


    override fun onDestroy() {
        super.onDestroy()
        // Putuskan koneksi saat Activity dihancurkan
//        webSocketManager.disconnect()
    }
}
