package com.apps.gmb.bussineslogic

import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.apps.gmb.R
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun <T : ViewBinding> Fragment.viewBinding(bind: (View) -> T): ReadOnlyProperty<Fragment, T> =
    viewBinding(
        R.id.main_layout, bind
    )

fun <T : ViewBinding> Fragment.viewBinding(
    @IdRes uniqueId: Int,
    bind: (View) -> T
): ReadOnlyProperty<Fragment, T> = ViewBindingWhichCachesBindingInFragmentViewTag(uniqueId, bind)

class ViewBindingWhichCachesBindingInFragmentViewTag<T : ViewBinding>(
    @IdRes private val uniqueId: Int,
    private val bind: (View) -> T
) : ReadOnlyProperty<Fragment, T> {
    /**
     * We'll continue to leak the view until this is removed.
     * Keep this around until we're sure we're not going to crash in production
     */

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        val view = thisRef.view
        return if (view == null) {
            throw IllegalStateException(
                "Accessed %s while in %s".format(
                    property,
                    thisRef.lifecycle.currentState
                )
            )
        } else {
            view.cachedBinding ?: bind(thisRef.requireView()).also { binding ->
                view.saveCachedBinding(binding)
            }
        }
    }

    private fun View.saveCachedBinding(binding: T) {
        setTag(uniqueId, binding)
    }

    @Suppress("UNCHECKED_CAST")
    private val View.cachedBinding: T?
        get() = getTag(uniqueId) as? T
}