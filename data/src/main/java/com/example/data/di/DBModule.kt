package com.example.data.di

import android.app.Application
import androidx.room.Room
import com.example.data.db.BreakingBadDatabase
import com.example.data.db.CharactersDao
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DBModule {

    @Provides
    @Singleton
    fun database(app: Application): BreakingBadDatabase =
        Room.databaseBuilder(app, BreakingBadDatabase::class.java, "star-wars-database").build()
}