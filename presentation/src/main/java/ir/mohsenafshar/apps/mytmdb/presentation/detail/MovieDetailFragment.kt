package ir.mohsenafshar.apps.mytmdb.presentation.detail

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ir.mohsenafshar.apps.mytmdb.presentation.R
import ir.mohsenafshar.apps.mytmdb.presentation.base.BaseFragment
import ir.mohsenafshar.apps.mytmdb.presentation.databinding.FragmentMovieDetailBinding

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding>() {
    override val layoutId: Int = R.layout.fragment_movie_detail

    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateBinding(savedInstanceState: Bundle?) {
        initView()
    }

    private fun initView() {
        binding.vm = viewModel
    }
}