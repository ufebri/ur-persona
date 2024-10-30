package semicolon.dev.yourpersona.presentation.ui.khodam

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import febri.uray.bedboy.core.data.Resource
import semicolon.dev.yourpersona.databinding.FragmentCekKhodamBinding
import semicolon.dev.yourpersona.model.KhodamMapper

@AndroidEntryPoint
class CekKhodamFragment : Fragment() {

    private val binding by lazy { FragmentCekKhodamBinding.inflate(layoutInflater) }
    private val viewModel: KhodamViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            binding.apply {
                btnSubmit.setOnClickListener {

                    //Name need validation to reduce usage trying
                    if (etName.text.toString().isNotEmpty())
                        viewModel.getKhodam(
                            etName.text.toString(),
                            KhodamMapper.randomKhodamName()
                        ).observe(viewLifecycleOwner) { mData ->
                            when (mData) {
                                is Resource.Loading -> showLoading(true)
                                is Resource.Success -> mData.let {
                                    tvResult.apply {
                                        text = it.data?.userKhodamDesc
                                        isVisible = true
                                    }
                                    showLoading(false)
                                }

                                is Resource.Error -> {
                                    tvResult.text = "There something error"
                                    showLoading(false)
                                }
                            }
                        }
                    else
                        Toast.makeText(requireActivity(), "Isi Nama Dulu", Toast.LENGTH_LONG).show()

                }
            }
        }
    }

    private fun showLoading(state: Boolean) {
        binding.apply {
            pb.visibility = if (state) View.VISIBLE else View.GONE
            tvResult.visibility = if (state) View.GONE else View.VISIBLE
        }
    }
}