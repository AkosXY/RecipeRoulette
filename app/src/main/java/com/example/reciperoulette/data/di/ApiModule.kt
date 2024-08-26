package com.example.reciperoulette.data.di

import com.example.reciperoulette.data.dao.RecipeDao
import com.example.reciperoulette.data.datasource.RecipeRepository
import com.example.reciperoulette.network.RecipeApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient) :Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesRecipeApiService(retrofit: Retrofit): RecipeApiService {
        return retrofit.create(RecipeApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRecipeRepository(apiService: RecipeApiService, recipeDao: RecipeDao): RecipeRepository {
        return RecipeRepository(apiService, recipeDao)
    }
}