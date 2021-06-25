package com.santukis.navigator

import android.content.Context
import android.content.Intent

abstract class ActivityFactory {

    abstract fun getIntent(context: Context): Intent

    open fun getAnimations(): TransitionAnimation = TransitionAnimation.NO_ANIMATIONS
}