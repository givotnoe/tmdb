package io.stoptalking.pet_tmdb._presentation.view.base.adapter

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<D, V : RecyclerView.ViewHolder> (context : Context) : RecyclerView.Adapter<V>() {

    protected val items : MutableList<D> = ArrayList()
    protected val inflater = LayoutInflater.from(context)

    override fun getItemCount() = items.size

    override fun onFailedToRecycleView(holder: V): Boolean {
        return true
    }

    //own

    fun getItem (position : Int) : D = items[position]

    fun addItem (item : D) {
        items.add(item)
        notifyItemInserted(items.lastIndex)
    }

    fun addItems(items: Collection<D>) {

        val pos = this.items.size

        this.items.addAll(items)
        notifyItemRangeInserted(pos, items.size)
    }

    fun replaceItems(items: Collection<D>) {

        this.items.clear()
        this.items.addAll(items)

        notifyDataSetChanged()
    }

    fun clearItems() {

        val size = items.size

        items.clear()
        notifyItemRangeRemoved(0, size)
    }
}