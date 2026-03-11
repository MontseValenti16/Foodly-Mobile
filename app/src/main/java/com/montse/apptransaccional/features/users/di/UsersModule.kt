package com.montse.apptransaccional.features.users.di

import com.montse.apptransaccional.core.network.RestaurantApi
import com.montse.apptransaccional.features.users.data.repositories.UserRepositoryImpl
import com.montse.apptransaccional.features.users.domain.repositories.UserRepository
import com.montse.apptransaccional.features.users.domain.usecases.CreateUserUseCase
import com.montse.apptransaccional.features.users.domain.usecases.DeleteUserUseCase
import com.montse.apptransaccional.features.users.domain.usecases.GetUserByIdUseCase
import com.montse.apptransaccional.features.users.domain.usecases.GetUsersUseCase
import com.montse.apptransaccional.features.users.domain.usecases.UpdateUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UsersModule {

    @Provides
    @Singleton
    fun provideUserRepository(api: RestaurantApi): UserRepository {
        return UserRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideGetUsersUseCase(repository: UserRepository): GetUsersUseCase {
        return GetUsersUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetUserByIdUseCase(repository: UserRepository): GetUserByIdUseCase {
        return GetUserByIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideCreateUserUseCase(repository: UserRepository): CreateUserUseCase {
        return CreateUserUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateUserUseCase(repository: UserRepository): UpdateUserUseCase {
        return UpdateUserUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteUserUseCase(repository: UserRepository): DeleteUserUseCase {
        return DeleteUserUseCase(repository)
    }
}
