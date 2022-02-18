package dev.illwiz.tada.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import dev.illwiz.tada.R
import dev.illwiz.tada.data.utils.LayoutUtils
import dev.illwiz.tada.databinding.ItemArtBinding
import dev.illwiz.tada.domain.art.Art

class ArtAdapter(
    private val glideRequest: RequestManager,
    private val callback: Callback
) : PagingDataAdapter<Art, RecyclerView.ViewHolder>(
    diffCallback = object : DiffUtil.ItemCallback<Art>() {
        override fun areItemsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem.objectNumber == newItem.objectNumber
        }
        override fun areContentsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemArtBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ArtHolder(binding,callback,glideRequest)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ArtHolder) {
            holder.bind(position, getItem(position)!!)
        }
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        if(holder is ArtHolder) {
            holder.recycle()
        }
    }

    internal class ArtHolder(
        private val binding: ItemArtBinding,
        private val callback: Callback,
        private val glideRequest:RequestManager
    ) : RecyclerView.ViewHolder(binding.root) {
        private var pos = 0
        private lateinit var item: Art

        fun bind(pos: Int, itemArg: Art) {
            this.pos = pos
            this.item = itemArg
            glideRequest
                .load(item.image)
                .placeholder(R.drawable.placeholder)
                .transform(RoundedCorners(LayoutUtils.dpToPx(10f,itemView.context).toInt()))
                .into(binding.mainImg)
            binding.titleTxt.text = item.title
            itemView.setOnClickListener {
                callback.onItemClick(pos, item)
            }
        }

        fun recycle() {
            glideRequest.clear(binding.mainImg)
        }
    }

    interface Callback {
        fun onItemClick(position: Int, item: Art)
    }
}
