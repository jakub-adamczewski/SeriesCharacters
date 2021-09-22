package com.example.domain.model.mappers

interface Mapper<From, To> {
    fun map(from: From): To
}