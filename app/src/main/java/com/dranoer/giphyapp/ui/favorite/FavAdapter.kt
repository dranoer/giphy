package com.dranoer.giphyapp.ui.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dranoer.giphyapp.R
import com.dranoer.giphyapp.data.model.Giphy

class FavAdapter constructor(
    private val onItemClickListener: ((giphy: Giphy) -> Unit)?
) : ListAdapter<Giphy, FavAdapter.FavViewHolder>(FAV_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.favorite_item, parent, false)
        return FavViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FavViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageItemView: ImageView = itemView.findViewById(R.id.favorite_image)
        private val buttonItemView: ImageButton = itemView.findViewById(R.id.favorite_button)

        init {
            buttonItemView.setOnClickListener {
                val item = getItem(adapterPosition)
                onItemClickListener?.invoke(item)
            }
        }

        fun bind(giphy: Giphy) {
            Glide
                .with(itemView.context)
                .load(giphy.previewUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_placeholder)
                .into(imageItemView)

            when (giphy.isFavorite) {
                true -> buttonItemView.setImageResource(R.drawable.ic_favorite)
                false -> buttonItemView.setImageResource(R.drawable.ic_unfavourite)
            }
        }
    }

    companion object {
        private val FAV_COMPARATOR = object : DiffUtil.ItemCallback<Giphy>() {
            override fun areItemsTheSame(oldItem: Giphy, newItem: Giphy): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Giphy, newItem: Giphy): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}