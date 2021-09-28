package com.example.domain.repository

import app.cash.turbine.test
import com.example.data.api.ApiService
import com.example.data.db.BreakingBadDatabase
import com.example.data.db.CharactersDao
import com.example.data.util.TestUtil
import com.example.domain.model.Character
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

class CharactersRepositoryTest {

    private val api = mock<ApiService>()
    private val db = mock<BreakingBadDatabase>()
    private val charactersDao = mock<CharactersDao>()
    private lateinit var sut: CharactersRepository

    private val dbCharacters = listOf(
        TestUtil.createCharacter(1, "Mark", "Twain"),
        TestUtil.createCharacter(2, "Gilbert", "Mick"),
        TestUtil.createCharacter(3, "Dolly", "Part")
    )

    private val apiCharacters = mutableListOf(
        TestUtil.createCharacter(4, "Anthony", "Berrick")
    ).plus(dbCharacters)

    @Before
    fun setUp() {
        sut = CharactersRepository(api, db)
        whenever(db.charactersDao()).thenReturn(charactersDao)
    }

    @Test
    fun `when getCharacters called and db empty, then insert data from API`() = runBlocking {
        whenever(api.searchCharacters(any())).thenReturn(apiCharacters)
        whenever(charactersDao.searchCharactersByName(any())).thenReturn(flowOf(emptyList()))

        sut.getCharacters("")
//        .test {
//            assertEquals(emptyList<Character>(), awaitItem())
//        }

        verify(charactersDao, times(1)).insertCharacters(any())
    }


}