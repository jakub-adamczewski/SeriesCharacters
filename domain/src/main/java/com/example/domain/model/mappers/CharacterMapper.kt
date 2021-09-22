package com.example.domain.model.mappers

import com.example.data.model.Character

internal class CharacterMapper : Mapper<Character, com.example.domain.model.Character> {
    override fun map(from: Character): com.example.domain.model.Character {
        return com.example.domain.model.Character(
            from.id,
            from.name,
            from.nickname,
            from.birthday,
            from.img
        )
    }
}