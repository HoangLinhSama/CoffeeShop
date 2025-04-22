package com.hoanglinhsama.client.presentation.di

import com.hoanglinhsama.client.data.repository.AppRepositoryImplement
import com.hoanglinhsama.client.data.repository.AuthRepositoryImplement
import com.hoanglinhsama.client.data.repository.MainRepositoryImplement
import com.hoanglinhsama.client.data.repository.OnBoardingRepositoryImplement
import com.hoanglinhsama.client.domain.repository.AppRepository
import com.hoanglinhsama.client.domain.repository.AuthRepository
import com.hoanglinhsama.client.domain.repository.MainRepository
import com.hoanglinhsama.client.domain.repository.OnboardingRepository
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

    @Provides
    @Singleton
    fun provideAuthRepository(repositoryImplements: AuthRepositoryImplement): AuthRepository {
        return repositoryImplements
    }

    @Provides
    @Singleton
    fun provideOnboardingRepository(repositoryImplements: OnBoardingRepositoryImplement): OnboardingRepository {
        return repositoryImplements
    }

    @Provides
    @Singleton
    fun provideAppRepository(repositoryImplements: AppRepositoryImplement): AppRepository {
        return repositoryImplements
    }
}
