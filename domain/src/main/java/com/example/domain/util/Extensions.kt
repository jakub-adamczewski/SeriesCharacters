package com.example.domain.util

import com.example.domain.model.mappers.Mapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <From, To> Flow<List<From>>.applyMapper(mapper: Mapper<From, To>): Flow<List<To>> =
    this.map { list -> list.applyMapper(mapper) }

fun <From, To> List<From>.applyMapper(mapper: Mapper<From, To>): List<To> =
    this.map { mapper.map(it) }