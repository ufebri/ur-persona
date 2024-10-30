package semicolon.dev.yourpersona.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import febri.uray.bedboy.core.domain.model.RegFire
import semicolon.dev.yourpersona.databinding.ItemFirebaseIdBinding
import semicolon.dev.yourpersona.presentation.viewholder.CardViewHolder

class FireAdapter(private val onClick: (RegFire) -> Unit) :
    ListAdapter<RegFire, CardViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(
            ItemFirebaseIdBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RegFire>() {
            override fun areItemsTheSame(
                oldItem: RegFire, newItem: RegFire
            ): Boolean {
                return oldItem.firebaseID == newItem.firebaseID
            }

            override fun areContentsTheSame(
                oldItem: RegFire, newItem: RegFire
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}