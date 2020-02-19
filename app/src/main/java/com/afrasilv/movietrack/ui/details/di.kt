package com.afrasilv.movietrack.ui.details

import com.afrasilv.data.repository.CreditsRepository
import com.afrasilv.data.repository.MoviesRepository
import com.afrasilv.usecases.CheckIfMovieIsFav
import com.afrasilv.usecases.GetMovieCredits
import com.afrasilv.usecases.RemoveIfFav
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class DetailsFragmentModule {

    @Provides
    fun favoritesViewModelProvider(checkIfMovieIsFav: CheckIfMovieIsFav,
                                   removeIfFav: RemoveIfFav,
                                   getMovieCredits: GetMovieCredits
    ) = DetailsViewModel(checkIfMovieIsFav, removeIfFav, getMovieCredits)

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

@Subcomponent( modules = [(DetailsFragmentModule::class)])
interface DetailsFragmentComponent {
    val detailsViewModel: DetailsViewModel
}