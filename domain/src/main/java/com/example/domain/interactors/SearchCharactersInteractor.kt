package com.example.domain.interactors

import android.util.Log
import com.example.domain.model.Character
import com.example.domain.repository.CharactersRepository
import com.example.domain.util.Resource
import com.example.util.logItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchCharactersInteractor @Inject constructor(
    private val charactersRepository: CharactersRepository
) {

    companion object {
        private const val TAG = "SearchCharactersInteractor"
    }
    private val _characters = MutableSharedFlow<Resource<List<Character>>>()
    val characters: Flow<Resource<List<Character>>> = _characters
        .logItems(TAG, "characters")

    suspend fun searchCharacters(search: String) = withContext(Dispatchers.IO) {
        _characters.emitAll(charactersRepository.getCharacters(search))
    }
}