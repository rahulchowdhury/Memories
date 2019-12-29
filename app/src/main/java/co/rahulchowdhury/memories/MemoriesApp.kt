package co.rahulchowdhury.memories

import android.app.Application
import co.rahulchowdhury.memories.di.databaseModule
import co.rahulchowdhury.memories.di.galleryModule
import co.rahulchowdhury.memories.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MemoriesApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MemoriesApp)
            modules(
                listOf(
                    networkModule,
                    databaseModule,
                    galleryModule
                )
            )
        }
    }

}
