package com.rdso.railwayreservation

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.createBitmap
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(val context : Context, val trainDetails : ArrayList<Train>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    var onItemClicked : ((Train) -> Unit)? = null

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var trainImage : ImageView
        var trainName : TextView
        var departure : TextView
        var arrival : TextView
        var trainNumber : TextView

        init{
            trainName = itemView.findViewById(R.id.tv_train_name)
            departure = itemView.findViewById(R.id.tv_departure)
            arrival = itemView.findViewById(R.id.tv_arrival)
            trainImage = itemView.findViewById(R.id.image_train)
            trainNumber = itemView.findViewById(R.id.tv_train_number)
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.train_details_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        val train : Train = trainDetails[position]
        holder.trainName.text = trainDetails[position].trainName
        holder.departure.text = trainDetails[position].departureDate
        holder.arrival.text = trainDetails[position].arrivalDate
        holder.trainNumber.text = trainDetails[position].trainNo.toString()

        holder.itemView.setOnClickListener{
            onItemClicked?.invoke(train)
        }
    }

    override fun getItemCount(): Int {
        return trainDetails.size
    }


}