package ru.korolevss.kvartirka

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_flat.*
import ru.korolevss.kvartirka.flatadapter.FlatViewHolder.Companion.ADDRESS
import ru.korolevss.kvartirka.flatadapter.FlatViewHolder.Companion.BUILDING_TYPE
import ru.korolevss.kvartirka.flatadapter.FlatViewHolder.Companion.PHOTOS
import ru.korolevss.kvartirka.flatadapter.FlatViewHolder.Companion.PRICE
import ru.korolevss.kvartirka.flatadapter.FlatViewHolder.Companion.TITLE
import ru.korolevss.kvartirka.viewpageradapter.ViewPagerAdapter

class FlatActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flat)

        val arrayOfPhoto = intent.getStringArrayExtra(PHOTOS)!!
        viewPager2.adapter = ViewPagerAdapter(arrayOfPhoto)

        textViewTitleFlat.text = intent.getStringExtra(TITLE)
        textViewBuildingTypeFlat.text =
            """${getString(R.string.building_type)} ${intent.getStringExtra(BUILDING_TYPE)}"""
        textViewAddressFlat.text =
            """${getString(R.string.address)} ${intent.getStringExtra(ADDRESS)}"""
        textViewPriceFlat.text =
            """${getString(R.string.price)} ${intent.getStringExtra(PRICE)!!} ${getString(R.string.currency)}"""
    }
}