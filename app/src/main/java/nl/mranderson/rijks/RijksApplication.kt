package nl.mranderson.rijks

import android.app.Application
import nl.mranderson.rijks.data.api.apiModule
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RijksApplication : Application() {

    private companion object {
        const val BASE_URL = "https://www.rijksmuseum.nl/"
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@RijksApplication)
            modules(appModule, apiModule(BASE_URL.toHttpUrl()))
        }
    }

}