package com.example.domain.repository

import androidx.room.withTransaction
import com.example.data.api.ApiService
import com.example.data.db.BrakingBadDatabase
import com.example.domain.model.Character
import com.example.domain.model.mappers.CharacterMapper
import com.example.domain.util.Resource
import com.example.domain.util.applyMapper
import com.example.domain.util.networkBoundResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val api: ApiService,
    private val db: BrakingBadDatabase
) {
    private val charactersDao = db.charactersDao()

    fun getCharacters(search: String): Flow<Resource<List<Character>>> =
        networkBoundResource(
            query = {
                charactersDao.getPeople(search).applyMapper(CharacterMapper())
            },
            fetch = {
                api.searchCharacters(search)
            },
            saveFetchResult = { characters ->
                db.withTransaction {
                    charactersDao.insertPeople(characters)
                }
            }
        )
}