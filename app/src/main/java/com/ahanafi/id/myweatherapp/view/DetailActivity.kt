package com.ahanafi.id.myweatherapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ahanafi.id.myweatherapp.R
import com.ahanafi.id.myweatherapp.viewmodel.DataViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.items_location.*
import kotlinx.android.synthetic.main.items_location.tv_location_name

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: DataViewModel

    companion object{
        const val LOCATION_NAME = "location_name"
        const val LOCATION_POSITION = "location_position"
        const val LOCATION_ID = "location_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        viewModel = ViewModelProvider(this).get(DataViewModel::class.java)

        if(intent.extras != null) {
            progressbar.visibility = VISIBLE
            val name = intent.getStringExtra(LOCATION_NAME)
            val locationId = intent.getIntExtra(LOCATION_ID, 0)

            if (locationId > 0) {
                viewModel.getLocationDetail(locationId).observe(this, Observer { data ->
                    if(data != null) {
                        progressbar.visibility = GONE
                        tv_location_name.text = name
                        tv_temp.text = data.consolidatedWeather[0].theTemp.toString()
                    }
                })

            }

        } else {
            Toast.makeText(this, "Extra NULL", Toast.LENGTH_SHORT).show()
        }
    }
}
