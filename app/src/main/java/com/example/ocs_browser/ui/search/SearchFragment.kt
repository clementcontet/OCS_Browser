package com.example.ocs_browser.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.ocs_browser.R
import com.example.ocs_browser.databinding.SearchFragmentBinding

class SearchFragment : Fragment() {
    private lateinit var binding: SearchFragmentBinding

    companion object {
        fun newInstance() = SearchFragment()
    }

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchFragmentBinding.inflate(layoutInflater)

        binding.linkButton.setOnClickListener { findNavController().navigate(R.id.detailFragment) }

        return binding.root
    }
}