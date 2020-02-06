package com.afrasilv.movietrack.di

import com.afrasilv.data.repository.CastRepository
import com.afrasilv.data.repository.CreditsRepository
import com.afrasilv.data.repository.MoviesRepository
import com.afrasilv.usecases.*
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun checkIfMovieIsFavProvider(moviesRepository: MoviesRepository) =
        CheckIfMovieIsFav(moviesRepository)

    @Provides
    fun getFavoriteMoviesProvider(moviesRepository: MoviesRepository) =
        GetFavoriteMovies(moviesRepository)

    @Provides
    fun getMovieCreditsProvider(creditsRepository: CreditsRepository) =
        GetMovieCredits(creditsRepository)

    @Provides
    fun getMoviesFromPersonIdProvider(moviesRepository: MoviesRepository) =
        GetMoviesFromPersonId(moviesRepository)

    @Provides
    fun getPersonDataByIdProvider(castRepository: CastRepository) =
        GetPersonDataById(castRepository)

    @Provides
    fun getPopularMoviesProvider(moviesRepository: MoviesRepository) =
        GetPopularMovies(moviesRepository)

    @Provides
    fun removeIfFavProvider(moviesRepository: MoviesRepository) =
        RemoveIfFav(moviesRepository)

    @Provides
    fun searchMoviesByNameProvider(moviesRepository: MoviesRepository) =
        SearchMoviesByName(moviesRepository)

    @Provides
    fun searchPersonProvider(castRepository: CastRepository) =
        SearchPerson(castRepository)
}