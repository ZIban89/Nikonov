package com.example.tinkoffmovies.ui.main.filmslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffmovies.common.load
import com.example.tinkoffmovies.databinding.ItemFilmBinding
import com.example.tinkoffmovies.domain.model.FilmModel

class FilmsAdapter : ListAdapter<FilmModel, FilmsAdapter.FilmVH>(FilmDiffUtilCallback) {

    var onClickCallback: (Int) -> Unit = {}
    var onLongClickCallback: (Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmVH =
        FilmVH(
            ItemFilmBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: FilmVH, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener { onClickCallback(item.id) }
        holder.itemView.setOnLongClickListener {
            onLongClickCallback(item.id)
            true
        }
    }

    class FilmVH(
        private val binding: ItemFilmBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: FilmModel) {
            with(binding) {
                itemImage.load(model.poster)
                itemTitle.text = model.title
                itemDescription.text = model.description
                favoriteIcon.isVisible = model.isFavorite
            }
        }
    }

    object FilmDiffUtilCallback : DiffUtil.ItemCallback<FilmModel>() {

        override fun areItemsTheSame(oldItem: FilmModel, newItem: FilmModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: FilmModel, newItem: FilmModel) =
            oldItem == newItem
    }
}