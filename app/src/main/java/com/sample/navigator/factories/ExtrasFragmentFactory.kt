package com.sample.navigator.factories

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.santukis.navigator.FragmentFactory
import com.sample.navigator.R

class ExtrasFragmentFactory(private val fragment: Class<out Fragment>,
                            private val extras: Bundle): FragmentFactory() {

    override fun create(): Fragment {
        val instance = fragment.newInstance()
        instance.arguments = createArguments()
        return instance
    }

    override fun createArguments(): Bundle? = extras

    override fun getPortraitContainer(): Int = R.id.left_container

    override fun getLandscapeContainer(): Int = R.id.right_container

    override fun getBackStackTag(): String? = fragment.name
}