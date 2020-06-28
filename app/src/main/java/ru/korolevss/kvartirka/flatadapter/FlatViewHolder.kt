package ru.korolevss.kvartirka.flatadapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.flat_card.view.*
import ru.korolevss.kvartirka.FlatActivity
import ru.korolevss.kvartirka.R
import ru.korolevss.kvartirka.model.Flat


class FlatViewHolder(private val view: View, var list: MutableList<Flat>) : RecyclerView.ViewHolder(view) {

    private lateinit var listOfPhoto: MutableList<String>

    companion object {
        const val ADDRESS = "ADDRESS"
        const val BUILDING_TYPE = "BUILDING_TYPE"
        const val PRICE = "PRICE"
        const val PHOTOS = "PHOTOS"
        const val TITLE= "TITLE"
    }

    init {
        this.clickListener()
    }

    private fun clickListener() {
        view.setOnClickListener {
            if (adapterPosition != RecyclerView.NO_POSITION) {
                val flat = list[adapterPosition]
                with (it) {
                    val intent = Intent(context, FlatActivity::class.java)
                    intent.putExtra(ADDRESS, flat.address)
                    intent.putExtra(BUILDING_TYPE, flat.building_type)
                    intent.putExtra(PRICE, flat.prices.day)
                    intent.putExtra(TITLE, flat.title)
                    flat.photos.forEach { it1 ->
                        listOfPhoto.add(it1.url)
                    }
                    intent.putExtra(PHOTOS, listOfPhoto.toTypedArray())
                    context.startActivity(intent)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun bind(flat: Flat) {
        with(view) {
            textViewAddressMain.text = flat.address
            textViewPriceMain.text =
                flat.prices.day.toString() + context.getString(R.string.currency)

            Glide.with(this)
                .load(flat.photo_default.url)
                .placeholder(R.drawable.ic_baseline_photo_24)
                .error(R.drawable.ic_baseline_error_24)
                .override(192, 192)
                .centerCrop()
                .into(imageViewPost)
        }
    }

}