package co.rahulchowdhury.memories.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import co.rahulchowdhury.memories.R
import co.rahulchowdhury.memories.data.model.local.Photo
import co.rahulchowdhury.monet.loadUsingMonet
import kotlinx.android.synthetic.main.single_item_photo.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class GalleryAdapter : PagedListAdapter<Photo, GalleryAdapter.ViewHolder>(PHOTOS_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_item_photo, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onViewRecycled(holder: ViewHolder) {
        //holder.job.cancel()
    }

    class ViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        val job = Job()
        private val coroutineScope = CoroutineScope(Dispatchers.Main + job)

        fun bind(photo: Photo?) {
            photo?.let {
                loadUsingMonet(coroutineScope) {
                    url = it.originalUrl
                    targetImageView = itemView.photoItem
                }
            }
        }

    }

    companion object {
        private val PHOTOS_COMPARATOR = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(
                oldItem: Photo,
                newItem: Photo
            ): Boolean = oldItem.uuid == newItem.uuid

            override fun areContentsTheSame(
                oldItem: Photo,
                newItem: Photo
            ): Boolean = oldItem == newItem
        }
    }

}
