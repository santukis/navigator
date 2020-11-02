package com.santukis.navigator

import android.content.ActivityNotFoundException
import android.os.Build
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import java.lang.ref.WeakReference

abstract class Navigator(activity: AppCompatActivity) {

    protected val activity: WeakReference<AppCompatActivity> = WeakReference(activity)

    fun initializeScreenOrientation() {
        activity.get()?.requestedOrientation = getScreenOrientation()
    }

    fun openFragment(fragmentFactory: FragmentFactory) {
        val fragment = getFragment(fragmentFactory)
        showFragment(fragmentFactory, fragment)
    }

    fun openActivity(activityFactory: ActivityFactory,
                     onSuccess: () -> Unit = {  },
                     onError: (String) -> Unit = {  }) {
        activity.get()?.apply {
            try {
                startActivity(activityFactory.getIntent(this))
                onSuccess()

            } catch (exception: ActivityNotFoundException) {
                onError("Error: No Activity")
            }
        } ?: onError("Error: No Context")
    }

    fun openActivityForResult(activityFactory: ActivityFactory,
                              requestCode: Int,
                              onError: (String) -> Unit = {  }) {
        activity.get()?.apply {
            try {
                startActivityForResult(activityFactory.getIntent(this), requestCode)

            } catch (exception: ActivityNotFoundException) {
                onError("Error: No Activity")
            }
        }
    }

    fun closeCurrentFragment() {
        when {
            isLastFragment() -> activity.get()?.finish()
            else -> try {
                activity.get()?.supportFragmentManager?.popBackStackImmediate()
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }

    fun closeCurrentActivity() {
        activity.get()?.finish()
    }

    fun isFragmentVisible(fragment: Class<out Fragment>): Boolean {
        activity.get()?.supportFragmentManager?.fragments?.forEach {
            if (it.javaClass == fragment && it.isVisible){
                return true
            }
        }

        return false
    }

    private fun resetBackStackIfNeeded() {
        val size = activity.get()?.supportFragmentManager?.backStackEntryCount ?: 0

        if (size > 0) {
            for (index in 0 until size) {
                activity.get()?.supportFragmentManager?.popBackStack()
            }

            activity.get()?.supportFragmentManager?.executePendingTransactions()
        }
    }

    private fun getFragment(fragmentFactory: FragmentFactory): Fragment {
        val fragment = getStackedFragment(fragmentFactory)

        return fragment ?: fragmentFactory.create()
    }

    private fun getStackedFragment(fragmentFactory: FragmentFactory): Fragment? {
        val fragment = activity.get()?.supportFragmentManager
                ?.findFragmentByTag(fragmentFactory.getBackStackTag())

        return if (fragment != null) fragmentFactory.update(fragment) else null
    }

    private fun showFragment(fragmentFactory: FragmentFactory, fragment: Fragment) {
        when {
            fragment.isVisible -> refreshFragment(fragment)
            fragment is DialogFragment -> showDialogFragment(fragment, fragmentFactory)
            else -> loadFragment(fragmentFactory, fragment)
        }
    }

    private fun refreshFragment(fragment: Fragment) {
        val transaction = activity.get()?.supportFragmentManager?.beginTransaction()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            transaction?.setReorderingAllowed(false)
        }

        transaction?.detach(fragment)?.attach(fragment)?.commitAllowingStateLoss()
    }

    private fun showDialogFragment(fragment: DialogFragment, fragmentFactory: FragmentFactory) {
        activity.get()?.apply {
            fragment.show(supportFragmentManager, fragmentFactory.getBackStackTag())
        }
    }

    private fun loadFragment(fragmentFactory: FragmentFactory, fragment: Fragment) {
        try {
            getTransaction()?.apply {
                replace(getContainerFor(fragmentFactory), fragment, fragmentFactory.getBackStackTag())
                setUpTransactionAnimations(this, fragmentFactory)
                setUpTransactionBackStack(this, fragmentFactory)
                commitAllowingStateLoss()
            }

        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    private fun getTransaction(): FragmentTransaction? = activity.get()?.supportFragmentManager?.beginTransaction()

    private fun setUpTransactionBackStack(transaction: FragmentTransaction, fragmentFactory: FragmentFactory) {
        when(val tag = fragmentFactory.getBackStackTag()) {
            null -> transaction.disallowAddToBackStack()
            else -> transaction.addToBackStack(tag)
        }
    }

    private fun setUpTransactionAnimations(transaction: FragmentTransaction, fragmentFactory: FragmentFactory) {
        transaction.setCustomAnimations(
            fragmentFactory.getEnterAnimation(),
            fragmentFactory.getExitAnimation(),
            fragmentFactory.getPopEnterAnimation(),
            fragmentFactory.getPopExitAnimation()
        )
    }

    protected abstract fun getContainerFor(fragmentFactory: FragmentFactory): Int

    protected abstract fun isLastFragment(): Boolean

    abstract fun getScreenOrientation(): Int
}