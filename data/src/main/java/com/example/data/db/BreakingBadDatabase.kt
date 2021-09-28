package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.model.Character

@Database(entities = [Character::class], version = 1, exportSchema = false)
abstract class BreakingBadDatabase : RoomDatabase() {
    abstract fun charactersDao(): CharactersDao
}