package io.stoptalking.pet_tmdb._presentation.view.base

import android.content.Context
import android.os.Bundle
import android.text.Layout
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AlignmentSpan
import android.util.Log
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.fragment.app.DialogFragment
import io.stoptalking.pet_tmdb.utils.dagger.ActivityComponent

abstract class BaseDialogFragment<L : BaseDialogFragment.Listener> : DialogFragment() {

    //abstract
    protected abstract fun inject (component : ActivityComponent)

    //vars
    protected var listener : L? = null

    //dialog

    override fun onAttach(context: Context) {
        super.onAttach(context)

        listener = context as L
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //injecting
        inject((context as BaseActivity).component!!)
    }

    override fun onDetach() {

        listener = null

        super.onDetach()
    }

    //mvp

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

    //listener

    interface Listener
}