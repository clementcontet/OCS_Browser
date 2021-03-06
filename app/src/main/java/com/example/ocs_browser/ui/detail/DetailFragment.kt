package com.example.ocs_browser.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.ocs_browser.databinding.FragmentDetailBinding

import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.example.ocs_browser.R
import com.example.ocs_browser.repositories.ApiRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers


class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()

    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.apiRepository = ApiRepository
        viewModel.mainThreadScheduler = AndroidSchedulers.mainThread()
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_detail, container, false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(activity as AppCompatActivity, findNavController())

        viewModel.searchItem = args.searchItem
        binding.playButton.setOnClickListener {
            val action =
                DetailFragmentDirections.actionDetailFragmentToPlayerFragment(args.searchItem)
            findNavController().navigate(action)
        }

        return binding.root
    }
}