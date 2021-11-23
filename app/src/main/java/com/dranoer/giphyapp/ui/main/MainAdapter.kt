package com.dranoer.giphyapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dranoer.giphyapp.R
import com.dranoer.giphyapp.data.model.Giphy

class MainAdapter constructor(
    private val onClickListener: OnClickListener
) : ListAdapter<Giphy, MainAdapter.MainViewHolder>(MAIN_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.giphy_item, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageItemView: ImageView = itemView.findViewById(R.id.gif_image)
        private val titleItemView: TextView = itemView.findViewById(R.id.title_text)
        private val usernameItemView: TextView = itemView.findViewById(R.id.username_text)
        private val buttonItemView: ImageButton = itemView.findViewById(R.id.fav_button)

        init {
            buttonItemView.setOnClickListener {
                val item = getItem(adapterPosition)
                onClickListener.onClick(item.id)
            }
        }

        fun bind(giphy: Giphy) {
            Glide
                .with(itemView.context)
                .load(giphy.previewUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_placeholder)
                .into(imageItemView)

            titleItemView.text = giphy.title ?: ""
            usernameItemView.text = giphy.username ?: ""

            when (giphy.isFavorite) {
                true -> buttonItemView.setImageResource(R.drawable.ic_favorite)
                false -> buttonItemView.setImageResource(R.drawable.ic_unfavourite)
            }
        }
    }

    companion object {
        private val MAIN_COMPARATOR = object : DiffUtil.ItemCallback<Giphy>() {
            override fun areItemsTheSame(oldItem: Giphy, newItem: Giphy): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Giphy, newItem: Giphy): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    class OnClickListener(val clickListener: (id: String) -> Unit) {
        fun onClick(id: String) = clickListener(id)
    }
}