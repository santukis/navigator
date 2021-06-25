package com.santukis.navigator

import android.content.Context
import android.content.Intent

abstract class ActivityFactory {

    abstract fun getIntent(context: Context): Intent

    open fun getEnterAnimation(): Int = 0

    open fun getExitAnimation(): Int = 0
}