package com.dranoer.giphyapp.ui.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dranoer.giphyapp.R
import com.dranoer.giphyapp.data.model.GiphyEntity

class FavAdapter constructor(
    private val onClickListener: OnClickListener
) : ListAdapter<GiphyEntity, FavAdapter.FavViewHolder>(FAV_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        return FavViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(
            id = current.id,
            isFavorite = current.isFavorite,
        )
        holder.itemView.setOnClickListener {
            onClickListener.onClick(current.id)
        }
    }

    class FavViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val buttonItemView: ImageButton = itemView.findViewById(R.id.favorite_button)

        fun bind(id: String, isFavorite: Boolean) {

            when (isFavorite) {
                true -> buttonItemView.setImageResource(R.drawable.ic_favorite)
                false -> buttonItemView.setImageResource(R.drawable.ic_unfavourite)
            }
        }

        companion object {
            fun create(parent: ViewGroup): FavAdapter.FavViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.favorite_item, parent, false)
                return FavAdapter.FavViewHolder(view)
            }
        }
    }

    companion object {
        private val FAV_COMPARATOR = object : DiffUtil.ItemCallback<GiphyEntity>() {
            override fun areItemsTheSame(oldItem: GiphyEntity, newItem: GiphyEntity): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: GiphyEntity, newItem: GiphyEntity): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    class OnClickListener(val clickListener: (id: String) -> Unit) {
        fun onClick(id: String) = clickListener(id)
    }
}