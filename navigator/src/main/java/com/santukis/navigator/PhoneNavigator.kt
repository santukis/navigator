package com.santukis.navigator

import androidx.appcompat.app.AppCompatActivity

internal class PhoneNavigator(activity: AppCompatActivity) : Navigator(activity) {

    override fun getContainerFor(fragmentFactory: FragmentFactory): Int? = fragmentFactory.getPortraitContainer()

    override fun isLastFragment(): Boolean {
        return activity.get()?.supportFragmentManager?.backStackEntryCount ?: -1 <= 1
    }
}