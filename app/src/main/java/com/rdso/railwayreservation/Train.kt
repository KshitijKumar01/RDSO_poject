package com.rdso.railwayreservation

import android.os.Parcel
import android.os.Parcelable


data class Train(
    val trainNo: Int,
    val trainName: String,
    val startStation: String,
    val endStation: String,
    val departureDate: String,
    val arrivalDate: String,
    val departureTime: String,
    val arrivalTime: String,
    val searchString : String
    ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(trainNo)
        parcel.writeString(trainName)
        parcel.writeString(startStation)
        parcel.writeString(endStation)
        parcel.writeString(departureDate)
        parcel.writeString(arrivalDate)
        parcel.writeString(departureTime)
        parcel.writeString(arrivalTime)
        parcel.writeArray(arrayOf(searchString))
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Train> {
        override fun createFromParcel(parcel: Parcel): Train {
            return Train(parcel)
        }

        override fun newArray(size: Int): Array<Train?> {
            return arrayOfNulls(size)
        }
    }
}