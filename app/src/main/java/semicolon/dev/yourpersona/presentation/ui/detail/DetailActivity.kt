package semicolon.dev.yourpersona.presentation.ui.detail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import semicolon.dev.yourpersona.R
import semicolon.dev.yourpersona.databinding.ActivityDetailBinding
import semicolon.dev.yourpersona.model.Popup
import semicolon.dev.yourpersona.util.datastore.DataStoreHelper

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.apply {

            val mData: Popup? = intent.getParcelableExtra("detail_data")
            val key: String = intent.getStringExtra("key") ?: "promo"

            mData?.let {
                Glide.with(this@DetailActivity).load(it.linkImage).into(ivBanner)

                tvTitle.text = it.title
                tvBody.text = it.message

                tvPeriode.text = it.content?.ctaPeriod

                val isProgram = key == "program"

                btnReminder.apply {
                    setOnClickListener { finish() }
                    isVisible = isProgram
                }
                btnTransaksi.apply {
                    isVisible = isProgram
                    setOnClickListener {
                        runBlocking {
                            DataStoreHelper.removeFromList(
                                this@DetailActivity,
                                "contentList",
                                it
                            )
                        }
                    }
                }
            }
        }

    }
}