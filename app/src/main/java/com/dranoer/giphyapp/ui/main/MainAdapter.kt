package com.dranoer.giphyapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
        return MainViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(
            imageUrl = current.images.preview.imageUrl,
        )
        holder.itemView.setOnClickListener {
            onClickListener.onClick(current.title)
        }
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageItemView: ImageView = itemView.findViewById(R.id.image)

        fun bind(imageUrl: String?) {
            Glide
                .with(itemView.context)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_placeholder)
                .into(imageItemView)
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
        private val MAIN_COMPARATOR = object : DiffUtil.ItemCallback<Giphy>() {
            override fun areItemsTheSame(oldItem: Giphy, newItem: Giphy): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Giphy, newItem: Giphy): Boolean {
                return oldItem.title == newItem.title
            }
        }
    }

    class OnClickListener(val clickListener: (id: String) -> Unit) {
        fun onClick(id: String) = clickListener(id)
    }
}