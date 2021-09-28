package com.example.ocs_browser.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.ocs_browser.databinding.FragmentDetailBinding

import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.ocs_browser.R


class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    val args: DetailFragmentArgs by navArgs()

    companion object {
        fun newInstance() = DetailFragment()
    }

    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_detail, container, false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.searchItem = args.searchItem
        binding.playButton.setOnClickListener {
            val action =
                DetailFragmentDirections.actionDetailFragmentToPlayerFragment(args.searchItem)
            findNavController().navigate(action)
        }

        return binding.root
    }
}