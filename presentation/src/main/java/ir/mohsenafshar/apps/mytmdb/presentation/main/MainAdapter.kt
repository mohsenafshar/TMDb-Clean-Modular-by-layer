package ir.mohsenafshar.apps.mytmdb.presentation.main

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ir.mohsenafshar.apps.mytmdb.domain.model.MovieItem
import ir.mohsenafshar.apps.mytmdb.presentation.base.BaseViewHolder
import java.util.concurrent.Executors

class MainAdapter : ListAdapter<MovieItem, BaseViewHolder>(
    AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<MovieItem>() {
        override fun areItemsTheSame(
            oldItem: MovieItem,
            newItem: MovieItem
        ): Boolean {
            val condition = oldItem.id == newItem.id
            return condition
        }

        override fun areContentsTheSame(
            oldItem: MovieItem,
            newItem: MovieItem
        ): Boolean {
            val condition = oldItem.title == newItem.title && oldItem.releaseDate == newItem.releaseDate
            return condition
        }
    })
        .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
        .build()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val movieView = MovieView(parent.context)
        return BaseViewHolder(movieView)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        (holder.itemView as MovieView).setData(currentList[position])
    }
}