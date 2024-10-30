package semicolon.dev.yourpersona.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import semicolon.dev.yourpersona.databinding.ItemNotifBinding
import semicolon.dev.yourpersona.model.Popup
import semicolon.dev.yourpersona.presentation.viewholder.NotifViewHolder

class NotifAdapter(private val onClick: (Popup) -> Unit) : ListAdapter<Popup, NotifViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotifViewHolder {
        return NotifViewHolder(
            ItemNotifBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: NotifViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Popup>() {
            override fun areItemsTheSame(
                oldItem: Popup,
                newItem: Popup
            ): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(
                oldItem: Popup,
                newItem: Popup
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}