package com.example.domain.util

import kotlinx.coroutines.flow.*

inline fun <Result, Request> networkBoundResource(
    crossinline query: () -> Flow<Result>,
    crossinline fetch: suspend () -> Request,
    crossinline saveFetchResult: suspend (Request) -> Unit,
    crossinline shouldFetch: (Result) -> Boolean = { true }
) = flow {
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(Resource.Loading(data))

        try {
            saveFetchResult(fetch())
            query().map { Resource.Success(it) }
        } catch (throwable: Throwable) {
            query().map { Resource.Error(throwable, it) }
        }
    } else {
        query().map { Resource.Success(it) }
    }

    emitAll(flow)
}