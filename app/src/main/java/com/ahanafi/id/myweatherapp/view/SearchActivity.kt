package com.ahanafi.id.myweatherapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahanafi.id.myweatherapp.R
import com.ahanafi.id.myweatherapp.adapter.LocationAdapter
import com.ahanafi.id.myweatherapp.viewmodel.DataViewModel
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {
    private lateinit var viewModel: DataViewModel
    private lateinit var locationAdapter: LocationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        viewModel = ViewModelProvider(this).get(DataViewModel::class.java)

        iv_search.setOnClickListener{
            val locationString = et_search.text
            if(! locationString.isNullOrEmpty()) {
                viewModel.searchLocation(locationString.toString())
            }
        }

        locationAdapter = LocationAdapter(this)

        viewModel.showProgress.observe(this, Observer {
            if(it) progressbar.visibility = VISIBLE else progressbar.visibility = INVISIBLE
        })
        viewModel.locationList.observe(this, Observer { location ->
             locationAdapter.setLocation(location)
        })

        rv_search.apply {
            adapter = locationAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }
    }
}
