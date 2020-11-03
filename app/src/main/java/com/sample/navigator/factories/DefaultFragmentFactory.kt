package com.sample.navigator.factories

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.santukis.navigator.FragmentFactory
import com.sample.navigator.R

class DefaultFragmentFactory(private val fragment: Class<out Fragment>): FragmentFactory() {

    override fun create(): Fragment = fragment.newInstance()

    override fun createArguments(): Bundle? = null

    override fun getPortraitContainer(): Int = R.id.left_container

    override fun getLandscapeContainer(): Int = R.id.left_container

    override fun getBackStackTag(): String? = fragment.name
}