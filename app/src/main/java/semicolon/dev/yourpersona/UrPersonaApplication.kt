package semicolon.dev.yourpersona

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.inappmessaging.FirebaseInAppMessaging
import dagger.hilt.android.HiltAndroidApp
import semicolon.dev.yourpersona.util.firebase.FiamBuilder

@HiltAndroidApp
open class UrPersonaApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        //Initialize Firebase
        FirebaseApp.initializeApp(this)

        //Initialize Firebase In App Messaging (FIAM) Display
        val isFiamActive = false
        if (isFiamActive) {
            val fiamDisplay = FiamBuilder(this)
            FirebaseInAppMessaging.getInstance().setMessageDisplayComponent(fiamDisplay)
        }
    }
}