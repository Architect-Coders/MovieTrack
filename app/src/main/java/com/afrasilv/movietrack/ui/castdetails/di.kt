package com.afrasilv.movietrack.ui.castdetails

import com.afrasilv.data.repository.CastRepository
import com.afrasilv.data.repository.MoviesRepository
import com.afrasilv.usecases.GetMoviesFromPersonId
import com.afrasilv.usecases.GetPersonDataById
import com.afrasilv.usecases.SearchPerson
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.Dispatchers

@Module
class CastDetailsFragmentModule {

    @Provides
    fun favoritesViewModelProvider(
        searchPerson: SearchPerson,
        getPersonDataById: GetPersonDataById,
        getMoviesFromPersonId: GetMoviesFromPersonId
    ) = CastViewModel(searchPerson, getPersonDataById, getMoviesFromPersonId, Dispatchers.Main)

    @Provides
    fun searchPerson(castRepository: CastRepository) =
        SearchPerson(castRepository)

    @Provides
    fun getPersonDataById(castRepository: CastRepository) =
        GetPersonDataById(castRepository)

    @Provides
    fun getMoviesFromPersonId(moviesRepository: MoviesRepository) =
        GetMoviesFromPersonId(moviesRepository)

}

@Subcomponent(modules = [(CastDetailsFragmentModule::class)])
interface CastDetailsFragmentComponent {
    val castViewModel: CastViewModel
}