package com.example.tolkachev.di

import com.example.tolkachev.data.remote.TokenInterceptor
import com.example.tolkachev.data.remote.api.MovieApi
import com.example.tolkachev.data.remote.repository.MovieRepository
import com.example.tolkachev.presentation.ui.screen.list.MovieListViewModel
import com.example.tolkachev.presentation.ui.screen.movie.MovieViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

val appModule = module {

    single { TokenInterceptor() }

    single<OkHttpClient> {
        val tokenInterceptor: TokenInterceptor = get()

        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .callTimeout(120, TimeUnit.SECONDS)

        builder
            .addInterceptor(tokenInterceptor)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    single<Retrofit> {
        val builder = Retrofit.Builder()
            .baseUrl("https://kinopoiskapiunofficial.tech/api/v2.2/")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))

        val client: OkHttpClient = get()
        builder.client(client).build()
    }

    single { get<Retrofit>().create(MovieApi::class.java) }

    factory { MovieRepository(get()) }

    viewModel {
        MovieListViewModel(get())
    }
    viewModel {
        MovieViewModel(get(), get())
    }
}