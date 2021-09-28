package com.example.data.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.example.data.util.TestUtil
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CharactersReadWriteTest {

    private lateinit var db: BreakingBadDatabase
    private lateinit var charactersDao: CharactersDao

    private val characters = listOf(
        TestUtil.createCharacter(1, "John", "Gin"),
        TestUtil.createCharacter(2, "Michael", "Case"),
    )

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, BreakingBadDatabase::class.java
        ).build()
        charactersDao = db.charactersDao()

        runBlocking {
            charactersDao.insertCharacters(characters)
        }
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun whenNoQueryProvidedThenEmitAllCharacters() = runBlocking {
        charactersDao.searchCharactersByName("").test {
            assertEquals(characters, awaitItem())
        }
    }

    @Test
    fun whenNameQueriedThenFilterCharacters() = runBlocking {
        charactersDao.searchCharactersByName("Joh").test {
            assertEquals(listOf(characters[0]), awaitItem())
        }
    }
}