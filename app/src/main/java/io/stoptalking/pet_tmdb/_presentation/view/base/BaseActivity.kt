package io.stoptalking.pet_tmdb._presentation.view.base

import android.content.Context
import android.os.Bundle
import android.text.Layout
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AlignmentSpan
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import io.stoptalking.pet_tmdb.Tmdb
import io.stoptalking.pet_tmdb.utils.dagger.ActivityComponent
import io.stoptalking.pet_tmdb.utils.dagger.DaggerActivityComponent
import io.stoptalking.pet_tmdb.utils.dagger.modules.ActivityModule
import io.stoptalking.pet_tmdb.utils.dagger.modules.GlideModule

abstract class BaseActivity : AppCompatActivity() {

    //abstract
    protected abstract fun getContentResId() : Int
    protected abstract fun inject (activityComponent: ActivityComponent)

    //vars
    var component: ActivityComponent? = null

    //activity

    @CallSuper
    override fun onCreate(state: Bundle?) {

        //injection before super
        component = DaggerActivityComponent
            .builder()
            .appComponent((application as Tmdb).component)
            .activityModule(ActivityModule(this))
            .glideModule(GlideModule(this))
            .build()

        inject(component!!)

        //super
        super.onCreate(state)
        setContentView(getContentResId())
    }

    override fun onDestroy() {

        component = null

        super.onDestroy()
    }

    //own

    fun hideKeyboard() {

        val view = currentFocus

        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun showKeyboard(view: View) {

        view.postDelayed({

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val isShowing = imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)

            if (!isShowing) {
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
            }

        }, 150)

    }

    fun showMessage(tag: String, message: String) {

        val centeredMsg = SpannableString(message)

        centeredMsg.setSpan(AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                0, message.length - 1,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE)

        Toast.makeText(this, centeredMsg, Toast.LENGTH_SHORT).show()
        Log.d(tag, "error - $message")
    }

    fun addFragment (fragment : Fragment, tag : String, resId : Int, addToBackStack : Boolean) {

        if (getFragment(tag) != null) {
            return
        }

        var trans = supportFragmentManager
            .beginTransaction()
            .add(resId, fragment, tag)

        if (addToBackStack) {
            trans = trans.addToBackStack(null)
        }

        trans.commit()
    }

    fun replaceFragment (fragment : Fragment, tag : String, resId : Int, addToBackStack : Boolean) {

        var trans = supportFragmentManager
            .beginTransaction()
            .replace(resId, fragment, tag)

        if (addToBackStack) {
            trans = trans.addToBackStack(null)
        }

        trans.commit()
    }

    fun removeFragment (tag : String) {

        val fragment = getFragment(tag)

        fragment?.let {
            supportFragmentManager
                .beginTransaction()
                .remove(it)
                .commit()
        }
    }

    fun getFragment (tag : String) = supportFragmentManager.findFragmentByTag(tag)
}