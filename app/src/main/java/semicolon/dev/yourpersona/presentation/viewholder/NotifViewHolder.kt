package semicolon.dev.yourpersona.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import semicolon.dev.yourpersona.databinding.ItemNotifBinding
import semicolon.dev.yourpersona.model.Popup

class NotifViewHolder(private val binding: ItemNotifBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(mData: Popup, onClick: (Popup) -> Unit) {
        binding.apply {

            tvTitle.text = mData.title
            tvBody.text = mData.message

            Glide.with(root).load(mData.linkImage).into(ivBanner)

        }.root.setOnClickListener { onClick(mData) }
    }
}