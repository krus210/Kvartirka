package ru.korolevss.kvartirka.flatadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.korolevss.kvartirka.R
import ru.korolevss.kvartirka.model.Flat

class FlatAdapter(var list: MutableList<Flat>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    var loadMoreBtnClickListener: OnLoadMoreBtnClickListener? = null

    interface OnLoadMoreBtnClickListener {
        fun onLoadMoreBtnClicked()
    }

    companion object {
        private const val TYPE_ITEM_FLAT = 0
        private const val ITEM_FOOTER = 1
    }

    fun newPosts(newData: List<Flat>) {
        this.list.clear()
        this.list.addAll(newData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.flat_card, parent, false)
        return when (viewType) {
            ITEM_FOOTER -> FooterViewHolder(
                this,
                LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.item_load_more, parent, false)
            )
            else -> FlatViewHolder(view, list)

        }
    }

    override fun getItemCount(): Int {
        return list.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            list.size -> ITEM_FOOTER
            else -> TYPE_ITEM_FLAT
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position != list.size) {
            val flat = list[position]
            with(holder as FlatViewHolder) {
                bind(flat)
            }
        }
    }
}