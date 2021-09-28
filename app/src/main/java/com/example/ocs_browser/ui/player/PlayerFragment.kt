package com.example.ocs_browser.ui.player

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.ocs_browser.databinding.FragmentPlayerBinding
import com.example.ocs_browser.ui.detail.DetailFragment
import com.example.ocs_browser.ui.detail.DetailFragmentArgs
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer

const val videoUrl =
    "https://bitmovin-a.akamaihd.net/content/MI201109210084_1/mpds/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.mpd"


class PlayerFragment : Fragment() {
    private lateinit var binding: FragmentPlayerBinding
    val args: DetailFragmentArgs by navArgs()
    private lateinit var player: SimpleExoPlayer

    companion object {
        fun newInstance() = DetailFragment()
    }

    private val viewModel: PlayerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayerBinding.inflate(inflater, container, false)
        context?.let { createPlayer(it) }
        // args.searchItem

        return binding.root
    }

    fun createPlayer(context: Context) {
        player = SimpleExoPlayer.Builder(context).build()
        binding.player.setPlayer(player)
        val mediaItem: MediaItem = MediaItem.fromUri(videoUrl)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
        player.seekTo(viewModel.positionMs)
        if (!viewModel.isPlaying) {
            player.pause()
        }
    }

    override fun onDestroyView() {
        viewModel.positionMs = player.contentPosition
        viewModel.isPlaying = player.isPlaying
        player.release()
        super.onDestroyView()
    }
}