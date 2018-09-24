package com.toldas.sampleapplication.data.model

import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmObject

data class MapLocation(
        val latitude: Double,
        val longitude: Double,
        val address: String,
        val label: String,
        val distance: Double
) : RealmObject(), Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readDouble(),
            parcel.readDouble(),
            parcel.readString(),
            parcel.readString(),
            parcel.readDouble()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeDouble(latitude)
        dest.writeDouble(longitude)
        dest.writeString(address)
        dest.writeString(label)
        dest.writeDouble(distance)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MapLocation> {
        override fun createFromParcel(source: Parcel): MapLocation {
            return MapLocation(source)
        }

        override fun newArray(size: Int): Array<MapLocation?> {
            return arrayOfNulls(size)
        }
    }

}