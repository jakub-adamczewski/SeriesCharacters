package com.example.ui_search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ui_base.showToast
import com.example.ui_search.databinding.FragmentSearchBinding
import com.example.ui_search.list.CharactersAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val viewModel by viewModels<SearchViewModel>()
    private val binding by lazy { FragmentSearchBinding.bind(requireView()) }

    private val charactersAdapter by lazy { CharactersAdapter(itemClickedCallback) }
    private val itemClickedCallback: (itemId: Int) -> Unit
        get() = { itemId ->
            viewModel.setEvent(
                SearchContract.Event.OnItemClicked(itemId)
            )
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareViews(binding)
        observeState()
        observeEffects()
    }

    private fun observeState() = lifecycleScope.launchWhenStarted {
        viewModel.state.collect { state ->
            charactersAdapter.submitList(state.characters)
            binding.charactersPb.visibility = if (state.refreshing) View.VISIBLE else View.GONE
        }
    }

    private fun observeEffects() = lifecycleScope.launchWhenStarted {
        viewModel.effect.collect { effect ->
            when (effect) {
                is SearchContract.Effect.ShowClickedToast -> {
                    showToast("Clicked item with id: ${effect.itemId}")
                }
                is SearchContract.Effect.ShowErrorToast -> {
                    showToast("Error!")
                }
            }
        }
    }

    private fun prepareViews(binding: FragmentSearchBinding) {
        binding.apply {
            charactersRv.apply {
                adapter = charactersAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)

            }
            charactersSv.apply {
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextChange(query: String?): Boolean {
                        Log.d(TAG, "query: $query")
                        viewModel.setEvent(SearchContract.Event.OnQueryChanged(query ?: ""))
                        return true
                    }

                    override fun onQueryTextSubmit(p0: String?): Boolean = false
                })
            }
        }
    }

    companion object {
        private const val TAG = "SearchFragment"
    }

}