package com.ohmymovies.core.di

import android.content.Context
import com.ohmymovies.core.data.source.remote.network.MoviesApi
import com.ohmymovies.core.utils.SSLCertificateConfigurator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.X509TrustManager

object NetworkModules : BaseModule {
    override val modules: List<Module>
        get() = listOf(retrofitModule, serviceModule)

    private val serviceModule = module {
        single { get<Retrofit>().create(MoviesApi::class.java) }
    }

    private val retrofitModule = module {
        single { provideRetrofitSeekerService(provideOkHttpClient(androidContext())) }
    }

    private fun provideOkHttpClient(context : Context): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val trustManagerFactory = SSLCertificateConfigurator.getTrustManager(context)
        val trustManagers = trustManagerFactory.trustManagers
        if (trustManagers.size != 1 || trustManagers[0] !is X509TrustManager) {
            throw IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers))
        }
        val trustManager = trustManagers[0] as X509TrustManager
        return OkHttpClient.Builder()
            .sslSocketFactory(SSLCertificateConfigurator.getSSLConfiguration(context).socketFactory, trustManager)
            .addInterceptor(logging)
            .connectTimeout(30L, TimeUnit.SECONDS)
            .readTimeout(30L, TimeUnit.SECONDS)
            .writeTimeout(30L, TimeUnit.SECONDS)
            .build()
    }

    private fun provideRetrofitSeekerService(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}