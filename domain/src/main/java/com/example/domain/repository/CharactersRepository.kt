package com.example.domain.repository

import android.util.Log
import androidx.room.withTransaction
import com.example.data.api.ApiService
import com.example.data.db.BreakingBadDatabase
import com.example.domain.model.Character
import com.example.domain.model.mappers.CharacterMapper
import com.example.domain.util.Resource
import com.example.domain.util.applyMapper
import com.example.domain.util.networkBoundResource
import com.example.util.callOnEmit
import com.example.util.logItems
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val api: ApiService,
    private val db: BreakingBadDatabase
) {

    companion object {
        private const val TAG = "CharactersRepository"
    }

    private val charactersDao = db.charactersDao()

    fun getCharacters(search: String): Flow<Resource<List<Character>>> = networkBoundResource(
        query = {
            charactersDao.searchCharactersByName(search)
                .applyMapper(CharacterMapper())
                .callOnEmit { Log.d(TAG, "getCharacters query ${it.size}") }
                .logItems(TAG, "getCharacters query")
        },
        fetch = {
            api.searchCharacters(if (search.isEmpty()) null else search).also {
                Log.d(TAG, "fetch: $it")
            }
        },
        saveFetchResult = { characters ->
            Log.d(TAG, "insert: $characters")
            db.withTransaction {
                charactersDao.insertCharacters(characters)
            }
        }
    )
}