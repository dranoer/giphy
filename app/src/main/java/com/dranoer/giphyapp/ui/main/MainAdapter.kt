package com.dranoer.giphyapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dranoer.giphyapp.R
import com.dranoer.giphyapp.data.model.GiphyEntity

class MainAdapter constructor(
    private val onClickListener: OnClickListener
) : ListAdapter<GiphyEntity, MainAdapter.MainViewHolder>(MAIN_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(
//            imageUrl = current.images.preview.imageUrl,
            title = current.title ?: "",
            isFavorite = current.isFavorite,
//            username = current.user?.name ?: ""
        )
        holder.itemView.setOnClickListener {
            current.title?.let { it1 -> onClickListener.onClick(it1) }
        }
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        private val imageItemView: ImageView = itemView.findViewById(R.id.gif_image)
        private val titleItemView: TextView = itemView.findViewById(R.id.title_text)

        //        private val usernameItemView: TextView = itemView.findViewById(R.id.username_text)
        private val buttonItemView: ImageButton = itemView.findViewById(R.id.fav_button)

        fun bind(title: String?, isFavorite: Boolean) {
//            Glide
//                .with(itemView.context)
//                .load(imageUrl)
//                .centerCrop()
//                .placeholder(R.drawable.ic_placeholder)
//                .into(imageItemView)

            if (title != null) titleItemView.text = title
//            if (username != null) usernameItemView.text = username
            when (isFavorite) {
                true -> buttonItemView.setImageResource(R.drawable.ic_favorite)
                false -> buttonItemView.setImageResource(R.drawable.ic_unfavourite)
            }
        }

        companion object {
            fun create(parent: ViewGroup): MainViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.giphy_item, parent, false)
                return MainViewHolder(view)
            }
        }
    }

    companion object {
        private val MAIN_COMPARATOR = object : DiffUtil.ItemCallback<GiphyEntity>() {
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