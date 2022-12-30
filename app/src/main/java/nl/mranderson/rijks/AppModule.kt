package nl.mranderson.rijks

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.mranderson.rijks.data.CollectionDataSource
import nl.mranderson.rijks.data.CollectionRemoteDataSource
import nl.mranderson.rijks.data.CollectionRepositoryImpl
import nl.mranderson.rijks.data.mapper.ArtDetailsMapper
import nl.mranderson.rijks.data.mapper.CollectionMapper
import nl.mranderson.rijks.domain.CollectionRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindCollectionDataSource(
        collectionRemoteDataSource: CollectionRemoteDataSource
    ): CollectionDataSource

    @Binds
    abstract fun bindCollectionRepository(
        collectionRepositoryImpl: CollectionRepositoryImpl
    ): CollectionRepository

    companion object {

        @Provides
        fun provideArtMapper() = ArtDetailsMapper

        @Provides
        fun provideCollectionMapper() =  CollectionMapper

    }

}