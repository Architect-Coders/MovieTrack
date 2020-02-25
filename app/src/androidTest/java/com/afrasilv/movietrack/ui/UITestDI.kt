package com.afrasilv.movietrack.ui

import android.app.Application
import com.afrasilv.movietrack.data.retrofit.RetrofitAPI
import com.afrasilv.movietrack.di.AppModule
import com.afrasilv.movietrack.di.DataModule
import com.afrasilv.movietrack.di.MoviesComponent
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    DataModule::class,
    UiTestServerModule::class
])
interface UiTestComponent : MoviesComponent {

    val retrofitAPI: RetrofitAPI
    val mockWebServer: MockWebServer

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): UiTestComponent
    }

}

@Module
class UiTestServerModule {

    @Provides
    @Singleton
    @Named("baseUrlTest")
    fun baseUrlProvider(): String = "http://127.0.0.1:8080"


    @Provides
    @Singleton
    fun mockWebServerProvider(): MockWebServer {
        var mockWebServer:MockWebServer? = null
        val thread = Thread(Runnable {
            mockWebServer = MockWebServer()
            mockWebServer?.start(8080)
        })
        thread.start()
        thread.join()
        return mockWebServer ?: throw NullPointerException()
    }

    @Provides
    @Singleton
    fun retrofitTestAPIProvider(
        @Named("baseUrlTest") baseUrlTest: String): RetrofitTestAPI = RetrofitTestAPI(baseUrlTest)

}