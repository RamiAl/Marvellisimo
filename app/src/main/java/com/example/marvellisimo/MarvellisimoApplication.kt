package com.example.marvellisimo

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.example.marvellisimo.activity.Receiver.ReceiveItemsActivity
import com.example.marvellisimo.activity.character_details.CharacterDetailsActivity
import com.example.marvellisimo.activity.favorites.FavoritesActivity
import com.example.marvellisimo.activity.search.SearchActivity
import com.example.marvellisimo.activity.search_result.SearchResultActivity
import com.example.marvellisimo.activity.series_details.SeriesDetailsActivity
import com.example.marvellisimo.notification.TestService
import com.example.marvellisimo.repository.MarvelProvider
import com.mongodb.stitch.android.core.Stitch
import dagger.Component
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Singleton

@Singleton
@Component(modules = [MarvelProvider::class])
interface ApplicationComponent {
    fun inject(activity: FavoritesActivity)
    fun inject(activity: SearchActivity)
    fun inject(activity: CharacterDetailsActivity)
    fun inject(activityS: SeriesDetailsActivity)
    fun inject(activity: SearchResultActivity)
    fun inject(activity: ReceiveItemsActivity)
    fun inject(job: TestService)
}

class MarvellisimoApplication : Application() {

    companion object {
        lateinit var applicationComponent: ApplicationComponent
        const val CHANNEL_ID = "Marvellisimo"
    }

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.create()

        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .name("marvellisimo.realm")
            .schemaVersion(0)
            .deleteRealmIfMigrationNeeded()
            .build()

        Realm.setDefaultConfiguration(config)

        val realm = Realm.getDefaultInstance()
        realm.executeTransaction{ it .deleteAll()}

        Stitch.initializeDefaultAppClient("marvellisimo-xebqg")

        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val name = "Marvellisimo"
        val descriptionText = "Marvelilisimo"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

}