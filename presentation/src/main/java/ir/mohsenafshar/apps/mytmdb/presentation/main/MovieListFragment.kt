package ir.mohsenafshar.apps.mytmdb.presentation.main

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.mohsenafshar.apps.mytmdb.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import ir.mohsenafshar.apps.mytmdb.domain.interactor.GetMovieListUseCase
import ir.mohsenafshar.apps.mytmdb.presentation.R
import ir.mohsenafshar.apps.mytmdb.presentation.databinding.FragmentMovieListBinding
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MovieListFragment : BaseFragment<FragmentMovieListBinding>() {
    override val layoutId: Int = R.layout.fragment_movie_list

    private val viewModel: MovieListViewModel by viewModels()

    private lateinit var mainAdapter: MainAdapter
    private lateinit var calendar: Calendar

    override fun onCreateBinding(savedInstanceState: Bundle?) {
        initAdapter()
        initDateFilteringListeners()

        viewModel.movies.observe(viewLifecycleOwner, {
            Timber.tag("MY_LIST").d(it.toString())
            Log.d("MY_LIST", it.toString())
            mainAdapter.submitList(it)
        })

//        viewModel.getMovieList(params = GetMovieListUseCase.GetMovieParams(1, ""))
    }

    private fun initAdapter() {
        mainAdapter = MainAdapter()

        with(binding.recyclerView) {
            val localLayoutManager = LinearLayoutManager(context)
            layoutManager = localLayoutManager
            adapter = mainAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastPosition = layoutManager.findLastVisibleItemPosition()
                    if (lastPosition == mainAdapter.itemCount - 1) {
                        viewModel.loadNextPage()
                    }
                }
            })
        }
    }

    // todo: Create DatePickerFragment
    private fun initDateFilteringListeners() {
        calendar = Calendar.getInstance()

        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel()
        }

        binding.editText.setOnClickListener {
            DatePickerDialog(
                requireContext(), date, calendar
                    .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.editText.doAfterTextChanged {
            getFilteredMovies()
        }
    }

    private fun updateLabel() {
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.editText.setText(sdf.format(calendar.time))
    }

    private fun getFilteredMovies() {
        val query = binding.editText.text.toString()
        viewModel.setQuery(query)
    }
}