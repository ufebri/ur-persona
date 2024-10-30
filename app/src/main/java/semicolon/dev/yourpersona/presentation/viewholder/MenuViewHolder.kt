package semicolon.dev.yourpersona.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import semicolon.dev.yourpersona.databinding.ItemMenuBinding
import semicolon.dev.yourpersona.model.Menu

class MenuViewHolder(private val binding: ItemMenuBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(mData: Menu, onClick: (Menu) -> Unit) {
        binding.apply {

            tvTitle.text = mData.nameMenu
            root.setOnClickListener { onClick(mData) }
        }
    }
}