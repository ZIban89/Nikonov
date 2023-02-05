package com.example.tinkoffmovies.ui.main.filmslist

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tinkoffmovies.R
import com.example.tinkoffmovies.common.PAGE_SIZE
import com.example.tinkoffmovies.databinding.FragmentFilmsListBinding
import com.example.tinkoffmovies.domain.model.State
import com.example.tinkoffmovies.ui.main.FilmsViewModel
import com.example.tinkoffmovies.ui.main.filmdetails.FilmDetailsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FilmsListFragment : Fragment() {

    private var _binding: FragmentFilmsListBinding? = null
    private val binding: FragmentFilmsListBinding get() = _binding!!

    private val viewModel: FilmsViewModel by activityViewModels()
    private val adapter: FilmsAdapter by lazy { FilmsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setListeners()
        subscribe()
    }

    private fun init() {
        binding.filmsRv.adapter = adapter
    }

    private fun setListeners() {
        binding.progressLayout.setOnClickListener { }
        binding.filterFavoriteChips.setOnCheckedStateChangeListener { group, checkedIds ->
            when(checkedIds.first()){
                R.id.popular_chip -> { viewModel.switchFavoriteScreen(false)}
                R.id.favorite_chip -> { viewModel.switchFavoriteScreen(true) }
            }
        }
        binding.filmsRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (viewModel.filmsFlow.value !is State.Success) return
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount: Int = layoutManager.itemCount
                val lastVisibleItemPosition: Int = layoutManager.findLastVisibleItemPosition()
                if (lastVisibleItemPosition >= totalItemCount - 3
                    && lastVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE
                    && dy > 0
                ) {
                    viewModel.loadNextFilms()
                }

            }
        })
        adapter.onClickCallback = { showDetails(it) }
        adapter.onLongClickCallback = { viewModel.addToFavorite(it) }
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.updateFilms()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun subscribe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.filmsFlow.collectLatest {
                    when (it) {
                        is State.Loading -> {
                            showLoader()
                        }
                        is State.Error -> {
                            binding.error.text = it.error
                            showError(true)
                            hideLoader()
                        }
                        is State.Success -> {
                            adapter.submitList(it.data)
                            showError(false)
                            hideLoader()
                        }
                    }
                }
            }
        }
    }

    private fun showLoader() {
        binding.progressLayout.isVisible = true
        binding.progress.show()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            binding.swipeRefreshLayout.setRenderEffect(
                RenderEffect.createBlurEffect(
                    12f,
                    12f,
                    Shader.TileMode.CLAMP
                )
            )
        }
    }

    private fun hideLoader() {
        binding.progressLayout.isVisible = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            binding.swipeRefreshLayout.setRenderEffect(null)
        }
    }

    private fun showError(showError: Boolean) {
        binding.error.isVisible = showError
        binding.filmsRv.isVisible = !showError
    }

    private fun showDetails(id: Int) {
        viewModel.getFilmDetails(id)
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, FilmDetailsFragment())
            .addToBackStack("asdf")
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}