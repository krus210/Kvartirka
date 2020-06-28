package ru.korolevss.kvartirka.flatadapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_load_more.view.*

class FooterViewHolder(private val adapter: FlatAdapter, view: View) :
    RecyclerView.ViewHolder(view) {

    init {
        with (view) {
            buttonLoadMore.setOnClickListener {
                adapter.loadMoreBtnClickListener?.onLoadMoreBtnClicked()
            }
        }
    }
}