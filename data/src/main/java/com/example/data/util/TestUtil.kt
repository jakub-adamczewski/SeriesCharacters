package com.example.data.util

import com.example.data.model.Character

object TestUtil {

    fun createCharacter(testId: Int, testName: String, testNickname: String): Character {
        return Character(
            id = testId,
            name = testName,
            nickname = testNickname,
            birthday = "Unknown",
            img = ""
        )
    }

}