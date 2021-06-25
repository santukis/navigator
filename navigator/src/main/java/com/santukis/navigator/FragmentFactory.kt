package com.santukis.navigator

import android.os.Bundle
import androidx.fragment.app.Fragment

abstract class FragmentFactory {

    abstract fun create(): Fragment

    abstract fun createArguments(): Bundle?

    abstract fun getPortraitContainer(): Int

    abstract fun getLandscapeContainer(): Int

    abstract fun getBackStackTag(): String?

    open fun getAnimations(): TransitionAnimation = TransitionAnimation.NO_ANIMATIONS

    open fun getPopAnimations(): TransitionAnimation = TransitionAnimation.NO_ANIMATIONS

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