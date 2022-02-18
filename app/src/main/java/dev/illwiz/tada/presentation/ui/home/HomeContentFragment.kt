package dev.illwiz.tada.presentation.ui.home

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import dev.illwiz.tada.R
import dev.illwiz.tada.data.utils.AppUtils
import dev.illwiz.tada.data.utils.PagingLoadStateAdapter
import dev.illwiz.tada.data.utils.onDestroyNullable
import dev.illwiz.tada.databinding.FragmentHomeContentBinding
import dev.illwiz.tada.domain.art.Art
import dev.illwiz.tada.presentation.ui.BaseFragment
import dev.illwiz.tada.presentation.ui.detail.DetailFragment

@AndroidEntryPoint
class HomeContentFragment : BaseFragment(),ArtAdapter.Callback {
    private val binding by onDestroyNullable {
        FragmentHomeContentBinding.inflate(LayoutInflater.from(requireContext()))
    }
    private var initialized = false

    private val viewModel by viewModels<HomeContentViewModel>()
    private lateinit var adapter:ArtAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(initialized) return
        initialized = true

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = ArtAdapter(Glide.with(this),this)
        observeFlow(adapter.loadStateFlow) {
            when(it.refresh) {
                LoadState.Loading -> {
                    if(adapter.itemCount==0) {
                        (binding.progressBar.layoutParams as FrameLayout.LayoutParams).gravity = Gravity.CENTER
                    } else {
                        (binding.progressBar.layoutParams as FrameLayout.LayoutParams).gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
                    }
                    binding.progressBar.visibility = View.VISIBLE
                }
                is LoadState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    val error = it.refresh as LoadState.Error
                    Toast.makeText(requireContext(), AppUtils.getErrorMessage(error.error),Toast.LENGTH_LONG).show()
                }
                else -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
        val loadStateAdapter = adapter.withLoadStateFooter(PagingLoadStateAdapter {
            adapter.retry()
        })
        binding.recyclerView.adapter = loadStateAdapter

        observeFlow(viewModel.getArtCollection()) { // viewModel.getArtCollection() automatically requesting API
            adapter.submitData(it)
        }
    }

    override fun onItemClick(position: Int, item: Art) {
        findNavController().navigate(R.id.detailFragment, bundleOf(
            DetailFragment.ARG_ART_ID to item.objectNumber
        ))
    }
}