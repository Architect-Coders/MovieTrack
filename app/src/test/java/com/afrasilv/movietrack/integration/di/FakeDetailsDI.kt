package com.afrasilv.movietrack.integration.di

import com.afrasilv.data.repository.CreditsRepository
import com.afrasilv.data.repository.MoviesRepository
import com.afrasilv.movietrack.ui.details.DetailsViewModel
import com.afrasilv.usecases.CheckIfMovieIsFav
import com.afrasilv.usecases.GetMovieCredits
import com.afrasilv.usecases.RemoveIfFav
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.Dispatchers

@Module
class FakeDetailsFragmentModule {

    @Provides
    fun favoritesViewModelProvider(checkIfMovieIsFav: CheckIfMovieIsFav,
                                   removeIfFav: RemoveIfFav,
                                   getMovieCredits: GetMovieCredits
    ) = DetailsViewModel(checkIfMovieIsFav, removeIfFav, getMovieCredits, Dispatchers.Unconfined)

    @Provides
    fun checkIfMovieIsFav(moviesRepository: MoviesRepository) =
        CheckIfMovieIsFav(moviesRepository)

    @Provides
    fun removeIfFav(moviesRepository: MoviesRepository) =
        RemoveIfFav(moviesRepository)

    @Provides
    fun getMovieCredits(creditsRepository: CreditsRepository) =
        GetMovieCredits(creditsRepository)
}

@Subcomponent( modules = [(FakeDetailsFragmentModule::class)])
interface FakeDetailsFragmentComponent {
    val detailsViewModel: DetailsViewModel
}