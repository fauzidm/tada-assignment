package dev.illwiz.tada.presentation.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import dagger.hilt.android.AndroidEntryPoint
import dev.illwiz.tada.R
import dev.illwiz.tada.data.utils.TaskState
import dev.illwiz.tada.data.utils.onDestroyNullable
import dev.illwiz.tada.databinding.FragmentDetailBinding
import dev.illwiz.tada.domain.art.Art
import dev.illwiz.tada.presentation.ui.BaseFragment

@AndroidEntryPoint
class DetailFragment : BaseFragment() {
    companion object {
        const val ARG_ART_ID = "ARG_ART_ID"
    }
    private val binding by onDestroyNullable {
        FragmentDetailBinding.inflate(LayoutInflater.from(requireContext()))
    }
    private var initialized = false
    private val viewModel by viewModels<DetailViewModel>()

    private var artId:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        artId = requireArguments().getString(ARG_ART_ID)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(initialized) return
        initialized = true

        // Listen to DetailTask.FetchDetail events
        viewModel.taskResult<DetailTask.FetchDetail, Art>().observe(viewLifecycleOwner) {
            if(it.status == TaskState.Status.LOADING) {
                setLoading(true)
            } else if(it.status == TaskState.Status.SUCCESS) {
                Glide.with(this)
                    .load(it.data?.image)
                    .placeholder(R.drawable.placeholder)
                    .transform(CenterCrop())
                    .into(binding.mainImg)
                binding.titleTxt.text = it.data?.title
                setLoading(false)
            } else if(it.status == TaskState.Status.ERROR) {
                setLoading(false)
                Toast.makeText(requireContext(),it.message, Toast.LENGTH_LONG).show()
            }
        }

        if(artId.isNullOrBlank()) {
            Toast.makeText(requireContext(),"Unknown art id", Toast.LENGTH_LONG).show()
        } else {
            viewModel.fetchDetail(artId!!)
        }
    }

    private fun setLoading(loading:Boolean) {
        binding.root.interceptTouch = loading
        binding.progressBar.visibility= if(loading) View.VISIBLE else View.GONE
    }
}