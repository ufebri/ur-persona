package semicolon.dev.yourpersona.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import semicolon.dev.yourpersona.databinding.ItemMenuBinding
import semicolon.dev.yourpersona.model.Menu
import semicolon.dev.yourpersona.presentation.viewholder.MenuViewHolder

class MenuAdapter(private val onClick: (Menu) -> Unit) :
    ListAdapter<Menu, MenuViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder(
            ItemMenuBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Menu>() {
            override fun areItemsTheSame(
                oldItem: Menu,
                newItem: Menu
            ): Boolean {
                return oldItem.idMenu == newItem.idMenu
            }

            override fun areContentsTheSame(
                oldItem: Menu,
                newItem: Menu
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}