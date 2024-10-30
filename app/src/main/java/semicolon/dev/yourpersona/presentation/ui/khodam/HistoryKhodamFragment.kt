package semicolon.dev.yourpersona.presentation.ui.khodam

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import semicolon.dev.yourpersona.databinding.FragmentHistoryKhodamBinding
import semicolon.dev.yourpersona.model.KhodamMapper
import semicolon.dev.yourpersona.presentation.adapter.MenuAdapter

@AndroidEntryPoint
class HistoryKhodamFragment : Fragment() {

    private val binding by lazy { FragmentHistoryKhodamBinding.inflate(layoutInflater) }
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
                viewModel.history.observe(viewLifecycleOwner) { mData ->
                    rvHistory.apply {
                        layoutManager = LinearLayoutManager(requireActivity())
                        val mAdapter = MenuAdapter {}
                        mAdapter.submitList(KhodamMapper.generateKhodam(mData))
                    }
                }
            }
        }
    }
}