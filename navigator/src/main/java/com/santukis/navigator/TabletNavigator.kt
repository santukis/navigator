package com.santukis.navigator

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity

internal class TabletNavigator(activity: AppCompatActivity) : Navigator(activity) {

    override fun getContainerFor(fragmentFactory: FragmentFactory): Int? =
        when (isLandscape()) {
            true -> fragmentFactory.getLandscapeContainer()
            false -> fragmentFactory.getPortraitContainer()
        }

    override fun isLastFragment(): Boolean {
        return activity.get()?.let {
            when (isLandscape()) {
                true -> it.supportFragmentManager.backStackEntryCount <= 2
                false -> it.supportFragmentManager.backStackEntryCount <= 1
            }
        } ?: false
    }

    private fun isLandscape() =
        activity.get()?.resources?.configuration?.orientation == Configuration.ORIENTATION_LANDSCAPE

}