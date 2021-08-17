package com.example.aclass.pullrequests

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.aclass.MainActivity
import com.example.aclass.common.model.PullRequest
import com.example.aclass.databinding.FragmentPullRequestBinding
import com.example.aclass.pullrequests.PullRequestFragmentViewModel.ViewState
import dagger.hilt.android.AndroidEntryPoint
import com.google.android.material.transition.MaterialSharedAxis

@AndroidEntryPoint
class PullRequestFragment : Fragment() {

    private val viewModel by viewModels<PullRequestFragmentViewModel>()

    private var _binding: FragmentPullRequestBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupTransitions()
        observeViewState()
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
        //binding.recyclerView.adapter = PullRequestAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewState() {
        viewModel.viewState.observe(this, { viewState ->
            when (viewState) {
                is ViewState.Loading -> { showLoadingIndicator() }
                is ViewState.PullRequests -> { showPullRequests(viewState.pullRequests) }
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

    private fun showPullRequests(pullRequests: List<PullRequest>) {
        binding.progressIndicator.isVisible = false
        binding.recyclerView.isVisible = true
    }

    private fun setupTransitions() {
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
    }
}