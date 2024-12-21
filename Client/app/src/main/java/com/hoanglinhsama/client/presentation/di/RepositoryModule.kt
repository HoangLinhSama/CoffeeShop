package com.hoanglinhsama.client.presentation.di

import com.hoanglinhsama.client.data.repository.MainRepositoryImplement
import com.hoanglinhsama.client.domain.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideMainRepository(repositoryImplements: MainRepositoryImplement): MainRepository {
        return repositoryImplements
    }
}
