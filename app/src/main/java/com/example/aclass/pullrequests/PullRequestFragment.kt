package com.example.aclass.pullrequests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionManager
import com.example.aclass.MainActivity
import com.example.aclass.common.model.PullRequest
import com.example.aclass.databinding.FragmentPullRequestBinding
import com.example.aclass.pullrequests.PullRequestFragmentViewModel.ViewState
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PullRequestFragment : Fragment() {

    private val viewModel by viewModels<PullRequestFragmentViewModel>()

    private var _binding: FragmentPullRequestBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupTransitions()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPullRequestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewState()
        binding.recyclerView.adapter = PullRequestRecyclerAdapter()
        binding.checkbox.setOnCheckedChangeListener { _, isChecked -> viewModel.onChecked(isChecked) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewState() {
        viewModel.viewState.observe(viewLifecycleOwner, { viewState ->
            when (viewState) {
                is ViewState.Loading -> {
                    showLoadingIndicator()
                }
                is ViewState.PullRequests -> {
                    showPullRequests(viewState.pullRequestItems)
                }
                is ViewState.Error -> {
                    (requireActivity() as MainActivity).showSnackbar(viewState.stringId)
                    findNavController().popBackStack()
                }
            }
        })
    }

    private fun showLoadingIndicator() {
        binding.recyclerView.isVisible = false
        binding.progressIndicator.isVisible = true
    }

    private fun showPullRequests(pullRequestItems: List<PullRequest>) {
        val transition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        TransitionManager.beginDelayedTransition(view as ViewGroup, transition)

        (binding.recyclerView.adapter as PullRequestRecyclerAdapter).submitList(pullRequestItems)

        binding.progressIndicator.isVisible = false
        binding.recyclerView.isVisible = true

        binding.numResults.text = "${pullRequestItems.size} Results"
    }

    private fun setupTransitions() {
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
    }
}