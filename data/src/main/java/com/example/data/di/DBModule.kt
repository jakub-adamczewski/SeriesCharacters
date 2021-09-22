package com.example.data.di

import android.app.Application
import androidx.room.Room
import com.example.data.db.BrakingBadDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DBModule {

    @Provides
    @Singleton
    fun database(app: Application): BrakingBadDatabase =
        Room.databaseBuilder(app, BrakingBadDatabase::class.java, "star-wars-database").build()

}