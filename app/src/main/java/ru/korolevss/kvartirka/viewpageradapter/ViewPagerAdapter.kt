package ru.korolevss.kvartirka.viewpageradapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.flat_card.view.*
import kotlinx.android.synthetic.main.item_page.view.*
import ru.korolevss.kvartirka.R

class ViewPagerAdapter(private val arrayOfPhoto: Array<String>) : RecyclerView.Adapter<PagerVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH =
        PagerVH(LayoutInflater.from(parent.context).inflate(R.layout.item_page, parent, false))

    override fun getItemCount(): Int = arrayOfPhoto.size

    override fun onBindViewHolder(holder: PagerVH, position: Int) {
        holder.bind(arrayOfPhoto[position])
    }
}

class PagerVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(url: String) {
        with (itemView) {
            Glide.with(this)
                .load(url)
                .placeholder(R.drawable.ic_baseline_photo_24)
                .error(R.drawable.ic_baseline_error_24)
                .override(500, 500)
                .centerCrop()
                .into(imageViewSlider)
        }
    }
}