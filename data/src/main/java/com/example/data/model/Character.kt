package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "characters")
data class Character(
    @PrimaryKey @Json(name = "char_id") val id: Int,
    val name: String,
    val nickname: String,
    val birthday: String,
    val img: String
)
