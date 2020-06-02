package io.stoptalking.pet_tmdb._presentation.view.base

import android.content.Context
import android.os.Bundle
import android.text.Layout
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AlignmentSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import io.stoptalking.pet_tmdb.utils.dagger.ActivityComponent

abstract class BaseFragment<L : BaseFragment.Listener> : Fragment() {

    //abstract
    protected abstract fun getContentResId() : Int
    protected abstract fun inject (component : ActivityComponent)

    //vars
    protected var listener : L? = null

    //fragment

    override fun onAttach(context: Context) {
        super.onAttach(context)

        //listener
        listener = context as L

    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //inject
        inject((context as BaseActivity).component!!)
    }

    @CallSuper
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return if (getContentResId() == View.NO_ID) {
            null
        } else {
            inflater.inflate(getContentResId(), container, false)
        }
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //dispatch insets
        view.requestFitSystemWindows()
    }

    override fun onDetach() {

        listener = null

        super.onDetach()
    }

    //mvp

    fun hideKeyboard(view: View) {

        val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)

    }

    fun showKeyboard() {

        val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)

    }

    fun showKeyboard(editText : EditText) {

        context?.let {

            val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED)
        }
    }

    fun showMessage(tag: String? = null, message: String) {

        val centeredMsg = SpannableString(message)

        centeredMsg.setSpan(AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                0, message.length - 1,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE)

        Toast.makeText(context!!, centeredMsg, Toast.LENGTH_SHORT).show()

        if (tag != null) {
            Log.d(tag, "error - $message")
        }
    }

    fun addFragment (fragment : Fragment, tag : String) {

        if (getFragment(tag) != null) {
            return
        }

        childFragmentManager
            .beginTransaction()
            .add(fragment, tag)
            .commit()
    }

    fun addFragment (fragment : Fragment, tag : String, resId : Int, addToBackStack : Boolean) {

        if (getFragment(tag) != null) {
            return
        }

        var trans = childFragmentManager
            .beginTransaction()
            .add(resId, fragment, tag)

        if (addToBackStack) {
            trans = trans.addToBackStack(null)
        }

        trans.commit()
    }

    fun replaceFragment (fragment : Fragment, tag : String, resId : Int, addToBackStack : Boolean) {

        var trans = childFragmentManager
            .beginTransaction()
            .replace(resId, fragment, tag)

        if (addToBackStack) {
            trans = trans.addToBackStack(null)
        }

        trans.commit()
    }

    fun getFragment (tag : String) = childFragmentManager.findFragmentByTag(tag)

    fun removeFragment (tag : String) {

        val fragment = getFragment(tag)

        fragment?.let {
            childFragmentManager
                .beginTransaction()
                .remove(it)
                .commit()
        }
    }

    //listener

    interface Listener
}