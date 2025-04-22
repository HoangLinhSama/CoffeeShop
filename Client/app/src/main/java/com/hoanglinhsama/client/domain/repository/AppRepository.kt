package com.hoanglinhsama.client.domain.repository

import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun checkFirstTimeEnterApp(): Flow<Boolean>
    fun checkSignedIn(): Flow<Boolean>
}