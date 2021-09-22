package com.example.ui_search

import androidx.lifecycle.viewModelScope
import com.example.domain.interactors.SearchCharactersInteractor
import com.example.domain.util.Resource
import com.example.ui_base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchCharactersInteractor: SearchCharactersInteractor
) : BaseViewModel<SearchContract.Event, SearchContract.State, SearchContract.Effect>() {

    private val searchQuery = MutableStateFlow("")

    override val state: StateFlow<SearchContract.State> = combine(
        searchCharactersInteractor.characters,
        loadingCounter.flow
    ) { characters, refreshing ->
        SearchContract.State(
            characters = characters.data ?: emptyList(),
            refreshing = refreshing && characters.data?.isEmpty() ?: false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SearchContract.State.EMPTY
    )

    init {
        viewModelScope.launch {
            searchQuery.debounce(300)
                .collectLatest { query ->
                    val job = launch {
                        loadingCounter.addLoader()
                        searchCharactersInteractor.searchCharacters(query)
                    }
                    job.invokeOnCompletion { loadingCounter.removeLoader() }
                    job.join()
                }
        }
    }

    override fun handleEvent(event: SearchContract.Event) {
        when (event) {
            is SearchContract.Event.OnQueryChanged -> {
                searchQuery.value = event.query
            }
            is SearchContract.Event.OnItemClicked -> {
                setEffect { SearchContract.Effect.ShowClickedToast(event.itemId) }
            }
        }
    }
}