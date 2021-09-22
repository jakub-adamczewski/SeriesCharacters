package com.example.ui_search

import com.example.domain.model.Character
import com.example.ui_base.UiEffect
import com.example.ui_base.UiEvent
import com.example.ui_base.UiState

class SearchContract {

    sealed class Event : UiEvent {
        data class OnQueryChanged(val query: String) : Event()
        data class OnItemClicked(val itemId: Int) : Event()
    }

    data class State(
        val query: String = "",
        val characters: List<Character> = emptyList(),
        val refreshing: Boolean = false
    ) : UiState {
        companion object {
            val EMPTY = State()
        }
    }

    sealed class Effect : UiEffect {
        data class ShowClickedToast(val itemId: Int) : Effect()
        object ShowErrorToast : Effect()
    }
}