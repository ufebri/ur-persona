package semicolon.dev.yourpersona.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import febri.uray.bedboy.core.domain.model.RegFire
import semicolon.dev.yourpersona.databinding.ItemFirebaseIdBinding

class CardViewHolder(private val binding: ItemFirebaseIdBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(mData: RegFire, onClick: (RegFire) -> Unit) {
        binding.apply {

            tvTimestamp.text = mData.timestamp
            tvFireID.text = mData.firebaseID

            root.setOnClickListener { onClick(mData) }
        }
    }
}