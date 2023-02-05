package com.example.tinkoffmovies.ui.main.filmdetails

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
import com.example.tinkoffmovies.common.load
import com.example.tinkoffmovies.databinding.FragmentFilmDetailsBinding
import com.example.tinkoffmovies.domain.model.FilmDetailsModel
import com.example.tinkoffmovies.domain.model.State
import com.example.tinkoffmovies.ui.main.FilmsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FilmDetailsFragment : Fragment() {

    private var _binding: FragmentFilmDetailsBinding? = null
    private val binding: FragmentFilmDetailsBinding get() = _binding!!

    private val viewModel: FilmsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.filmDetailsFlow.collectLatest {
                    when (it) {
                        is State.Loading -> showLoader()
                        is State.Error -> showError(it.error)
                        is State.Success -> it.data?.let {
                            showContent(it)
                        }
                    }
                }
            }
        }
    }


    private fun setListeners() {
        binding.backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun populate(filmDetails: FilmDetailsModel) {
        binding.poster.load(filmDetails.poster)
        binding.title.text = filmDetails.title
        binding.description.text = filmDetails.description
        binding.genre.text = filmDetails.genres
        binding.country.text = filmDetails.countries
    }

    private fun showLoader() {
        binding.content.isVisible = false
        binding.error.isVisible = false
        binding.progress.show()
    }

    private fun showError(text: String?) {
        binding.content.isVisible = false
        binding.progress.hide()
        binding.error.text = text
        binding.error.isVisible = true
    }

    private fun showContent(filmDetails: FilmDetailsModel) {
        binding.error.isVisible = false
        binding.progress.hide()
        populate(filmDetails)
        binding.content.isVisible = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}