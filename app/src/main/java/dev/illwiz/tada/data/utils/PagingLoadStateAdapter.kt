package dev.illwiz.tada.data.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.illwiz.tada.R
import dev.illwiz.tada.databinding.ItemLoadStateBinding

/**
 * https://developer.android.com/topic/libraries/architecture/paging/v3-paged-data#load-state-adapter
 */
open class PagingLoadStateAdapter(
	private val retry: () -> Unit
) : LoadStateAdapter<PagingLoadStateAdapter.LoadStateViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
		return LoadStateViewHolder(parent, retry)
	}

	override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
		holder.bind(loadState)
	}

	inner class LoadStateViewHolder(
		parent: ViewGroup,
		retry: () -> Unit
	) : RecyclerView.ViewHolder(
		LayoutInflater.from(parent.context).inflate(R.layout.item_load_state, parent, false)
	) {
		private val binding = ItemLoadStateBinding.bind(itemView)
		private val progressBar: ProgressBar = binding.progressBar
		private val errorMsg: TextView = binding.errorTxt
		private val retry: Button = binding.retryBtn
			.also {
				it.setOnClickListener { retry() }
			}

		fun bind(loadState: LoadState) {
			if (loadState is LoadState.Error) {
				//errorMsg.text = loadState.error.localizedMessage
				errorMsg.text = "We can't process your request"
			}

			progressBar.isVisible = loadState is LoadState.Loading
			retry.isVisible = loadState is LoadState.Error
			errorMsg.isVisible = loadState is LoadState.Error
		}
	}
}