package com.hoanglinhsama.client.presentation.di

import android.content.Intent
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object IntentModule {
    @Provides
    @Singleton
    fun provideIntentActionPick(): Intent {
        return Intent(Intent.ACTION_PICK)
            .apply {
                type = "image/*"
            }
    }
}