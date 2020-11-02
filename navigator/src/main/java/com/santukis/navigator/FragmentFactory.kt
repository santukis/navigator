package com.santukis.navigator

import android.os.Bundle
import androidx.fragment.app.Fragment

abstract class FragmentFactory {

    abstract fun create(): Fragment

    abstract fun createArguments(): Bundle?

    abstract fun getPortraitContainer(): Int

    abstract fun getLandscapeContainer(): Int

    abstract fun getBackStackTag(): String?

    open fun getEnterAnimation(): Int = 0

    open fun getExitAnimation(): Int = 0

    open fun getPopEnterAnimation(): Int = 0

    open fun getPopExitAnimation(): Int = 0

    fun update(fragment: Fragment): Fragment {
        val updatedArguments = createArguments()

        return updatedArguments?.run {
            when(fragment.arguments == null) {
                true -> fragment.arguments = updatedArguments
                false -> fragment.arguments?.putAll(updatedArguments)
            }
            fragment
        } ?: fragment
    }
}