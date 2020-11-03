package com.sample.navigator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.santukis.navigator.Navigator
import com.santukis.navigator.NavigatorFactory
import com.sample.navigator.factories.DefaultFragmentFactory
import com.sample.navigator.factories.ExtrasFragmentFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity: AppCompatActivity() {

    private val navigator: Navigator by lazy { NavigatorFactory().create(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        navigator.openFragment(DefaultFragmentFactory(FirstFragment::class.java))

        open_first_fragment?.setOnClickListener {
            navigator.openFragment(DefaultFragmentFactory(FirstFragment::class.java))
        }

        open_second_fragment?.setOnClickListener {
            navigator.openFragment((ExtrasFragmentFactory(
                fragment = SecondFragment::class.java,
                extras = Bundle().apply { putString("title", "Fragment with extras ${Random.nextInt()}") })))
        }
    }
}