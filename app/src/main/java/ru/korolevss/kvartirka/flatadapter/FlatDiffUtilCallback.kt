package ru.korolevss.kvartirka.flatadapter

import androidx.recyclerview.widget.DiffUtil
import ru.korolevss.kvartirka.model.Flat

class FlatDiffUtilCallback(
    private val oldList: MutableList<Flat>,
    private val newList: MutableList<Flat>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldModel = oldList[oldItemPosition]
        val newModel = newList[newItemPosition]
        return oldModel.id == newModel.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldModel = oldList[oldItemPosition]
        val newModel = newList[newItemPosition]
        return oldModel.photo_default.url == newModel.photo_default.url
                && oldModel.address == newModel.address
                && oldModel.prices.day == newModel.prices.day
    }

}