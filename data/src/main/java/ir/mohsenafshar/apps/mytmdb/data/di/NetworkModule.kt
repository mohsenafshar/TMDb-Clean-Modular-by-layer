package ir.mohsenafshar.apps.mytmdb.data.di

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.mohsenafshar.apps.mytmdb.data.remote.route.MovieApi
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    val API_KEY = "a447989f2b34e1193b1194c6265c3d3f"
    val BASE_URL = "https://api.themoviedb.org/3/"

    private val TIMEOUT = 10L

    @Singleton
    @Provides
    fun provideOkHttpCache(context: Context): Cache =
        Cache(context.cacheDir, (10 * 1024 * 1024).toLong())

    @Singleton
    @Provides
    @Named("logging")
    fun provideLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    @Named("header")
    fun provideHeaderInterceptor(): Interceptor =
        Interceptor { chain ->
            val request = chain.request()
            val newUrl = request.url.newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()
            val newRequest = request.newBuilder()
                .url(newUrl)
                .method(request.method, request.body)
                .build()
            chain.proceed(newRequest)
        }


    @Singleton
    @Provides
    fun provideOkHttpClient(
    @Named("logging") logging: Interceptor,
    @Named("header") header: Interceptor,
//        authenticator: Authenticator,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .apply {
                addInterceptor(logging)
                addInterceptor(header)
//                addNetworkInterceptor(StethoInterceptor())
//                addInterceptor(OkHttpProfilerInterceptor())
//                authenticator(authenticator)
            }
            .build()

    @Singleton
    @Provides
    fun provideAppRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Singleton
    @Provides
    fun provideMovieApi(retrofit: Retrofit): MovieApi =
        provideApi(retrofit)

    @Singleton
    @Provides
    fun provideGson(): Gson = Gson()

    inline fun <reified T> provideApi(retrofit: Retrofit): T {
        return retrofit.create(T::class.java)
    }
}