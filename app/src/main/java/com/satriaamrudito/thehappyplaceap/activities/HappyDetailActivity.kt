package com.satriaamrudito.thehappyplaceap.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.satriaamrudito.thehappyplaceap.R
import com.satriaamrudito.thehappyplaceap.model.HappyPlacesModel
import kotlinx.android.synthetic.main.activity_happy_detail.*

class HappyDetailActivity : AppCompatActivity() {

    private var happyPlaceDetailModel: HappyPlacesModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_happy_detail)

        happyPlaceDetailModel =
            intent.getParcelableExtra(MainActivity.EXTRA_PLACE_DETAIL)

        happyPlaceDetailModel?.let {
            setSupportActionBar(toolbar_detail_place)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = it.title

            toolbar_detail_place.setNavigationOnClickListener {
                onBackPressed()
            }

            iv_place_image.setImageURI(Uri.parse(it.image))
            tv_description.text = it.description
            tv_location.text = it.location

            btn_view_on_map.setOnClickListener {
                val intent = Intent(this, MapsActivity::class.java)
                intent.putExtra(MainActivity.EXTRA_PLACE_DETAIL, happyPlaceDetailModel)
                startActivity(intent)
            }
        }
    }
}
