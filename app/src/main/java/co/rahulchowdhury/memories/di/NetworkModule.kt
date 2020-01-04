package co.rahulchowdhury.memories.di

import co.rahulchowdhury.memories.BuildConfig
import co.rahulchowdhury.memories.data.Constants
import co.rahulchowdhury.memories.data.source.remote.RandomUsersApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { provideHttpLoggingInterceptor() }
    single { provideOkHttpClient(get()) }
    single { provideRetrofit(get()) }
    single { provideRandomUsersApiService(get()) }
}

private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    return httpLoggingInterceptor
}

private fun provideOkHttpClient(
    httpLoggingInterceptor: HttpLoggingInterceptor
): OkHttpClient {
    val okHttpClientBuilder = OkHttpClient.Builder()

    if (BuildConfig.DEBUG) {
        okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)
    }

    return okHttpClientBuilder.build()
}

private fun provideRetrofit(
    okHttpClient: OkHttpClient
): Retrofit =
    Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.Api.BASE_URL)
        .build()

private fun provideRandomUsersApiService(
    retrofit: Retrofit
): RandomUsersApiService = retrofit.create(RandomUsersApiService::class.java)
