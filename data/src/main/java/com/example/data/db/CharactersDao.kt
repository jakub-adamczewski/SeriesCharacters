package com.example.data.db

import androidx.room.*
import com.example.data.model.Character
import kotlinx.coroutines.flow.Flow

@Dao
interface CharactersDao {

    @Query("SELECT * FROM characters WHERE name LIKE :search")
    fun getPeople(search: String): Flow<List<Character>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPeople(characters: List<Character>)
}