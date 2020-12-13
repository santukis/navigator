package com.santukis.navigator

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity

class NavigatorFactory {

    fun create(activity: AppCompatActivity): Navigator {
        return when(isTablet(activity)) {
            true -> TabletNavigator(activity)
            false -> PhoneNavigator(activity)
        }
    }
}

fun isTablet(context: Context): Boolean {
    return (context.resources.configuration.screenLayout and
            Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE
}