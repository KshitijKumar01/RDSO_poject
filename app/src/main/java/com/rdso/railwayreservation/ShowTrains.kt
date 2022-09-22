package com.rdso.railwayreservation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rdso.railwayreservation.databinding.ActivityShowTrainsBinding
import java.util.*

class ShowTrains : AppCompatActivity() {
    private lateinit var binding : ActivityShowTrainsBinding
    private var trainList : ArrayList<Train>? = null
    private lateinit var tempArrayList : ArrayList<Train>

    private var recyclerView : RecyclerView? = null
    lateinit var recyclerAdapter: RecyclerAdapter
    lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowTrainsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        recyclerView = findViewById(R.id.recyclerViewTrains)

        trainList = Constants.getTrainList()
        tempArrayList = Constants.getTrainList()

        layoutManager = LinearLayoutManager(this)
        binding.recyclerViewTrains.layoutManager = layoutManager
        recyclerAdapter = RecyclerAdapter(baseContext, trainList!!)
        binding.recyclerViewTrains.adapter = recyclerAdapter

        tempArrayList.addAll(trainList!!)

        binding.startStation.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query : String?): Boolean {
                return false
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText : String?): Boolean {
                tempArrayList.clear()
                val searchText = newText!!.toLowerCase(Locale.getDefault())
                if(searchText.isNotEmpty()){
                    trainList!!.forEach{
                        if(it.searchString.toLowerCase(Locale.getDefault()).contains(searchText)){
                            tempArrayList.add(it)
                        }
                    }

                    recyclerAdapter = RecyclerAdapter(baseContext, tempArrayList)
                    recyclerAdapter.notifyDataSetChanged()
                    recyclerView?.adapter = recyclerAdapter
                }else{
                    tempArrayList.clear()
                    tempArrayList.addAll(trainList!!)
                    recyclerAdapter = RecyclerAdapter(baseContext, tempArrayList)
                    recyclerAdapter.notifyDataSetChanged()
                    recyclerView?.adapter = recyclerAdapter
                }

                return false
            }
        })


        recyclerAdapter.onItemClicked = {
            Constants.trainObj = it
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("wholeTrainDetails", it)
//            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
//            setResult(RESULT_OK, intent)
//            finish()
        }
    }

}
