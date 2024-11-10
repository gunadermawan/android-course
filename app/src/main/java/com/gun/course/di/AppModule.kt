package com.gun.course.di

import com.google.firebase.auth.FirebaseAuth
import com.gun.course.R
import com.gun.course.network.ApiService
import com.gun.course.viewmodel.AuthViewModel
import com.gun.course.viewmodel.UserViewmodel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.CertificateFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager


val appModule = module {
    single {

        val certificateFactory = CertificateFactory.getInstance("X.509")
        val inputStream = androidContext().resources.openRawResource(R.raw.cert)
        val certificate = inputStream.use {
            certificateFactory.generateCertificate(it)
        }

//        keystore
        val keyStore = java.security.KeyStore.getInstance(java.security.KeyStore.getDefaultType())
        keyStore.load(null, null)
        keyStore.setCertificateEntry("ca", certificate)

        val trustManagerFactory =
            TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(keyStore)
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, trustManagerFactory.trustManagers, java.security.SecureRandom())
        val client = OkHttpClient.Builder()
            .sslSocketFactory(
                sslContext.socketFactory,
                trustManagerFactory.trustManagers[0] as X509TrustManager
            )
            .build()
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    single {
        get<Retrofit>().create(ApiService::class.java)
    }

    viewModel { UserViewmodel(get()) }

    single { FirebaseAuth.getInstance() }

    viewModel { AuthViewModel(get()) }
}