package com.santukis.navigator

data class TransitionAnimation(
    val enterAnimation: Int = 0,
    val exitAnimation: Int = 0
) {
    companion object {
        val NO_ANIMATIONS = TransitionAnimation()
    }
}