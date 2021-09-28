package com.example.data.db

import androidx.room.*
import com.example.data.model.Character
import kotlinx.coroutines.flow.Flow

@Dao
interface CharactersDao {

    @Query("SELECT * FROM characters WHERE name LIKE  '%' || :query || '%'")
    fun searchCharactersByName(query: String): Flow<List<Character>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<Character>)
}