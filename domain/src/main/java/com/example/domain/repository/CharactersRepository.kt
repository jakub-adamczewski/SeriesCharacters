package com.example.domain.repository

import android.util.Log
import androidx.room.withTransaction
import com.example.data.api.ApiService
import com.example.data.db.BrakingBadDatabase
import com.example.domain.model.Character
import com.example.domain.model.mappers.CharacterMapper
import com.example.domain.util.Resource
import com.example.domain.util.applyMapper
import com.example.domain.util.networkBoundResource
import com.example.util.logItems
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val api: ApiService,
    private val db: BrakingBadDatabase
) {

    companion object {
        private const val TAG = "CharactersRepository"
    }

    private val charactersDao = db.charactersDao()

    fun getCharacters(search: String): Flow<Resource<List<Character>>> =
        networkBoundResource(
            query = {
                charactersDao.getPeople(search).applyMapper(CharacterMapper()).logItems(TAG)
            },
            fetch = {
                api.searchCharacters(search).also {
                    Log.d(TAG, "fetch: $it")
                }
            },
            saveFetchResult = { characters ->
                Log.d(TAG, "insert: $characters")
                db.withTransaction {
                    charactersDao.insertPeople(characters)
                }
            }
        )
}