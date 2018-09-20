package com.toldas.sampleapplication.data.model

import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmObject

data class MapLocation(
        private val latitude: Double,
        private val longitude: Double,
        private val address: String,
        private val name: String
) : RealmObject(), Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readDouble(),
            parcel.readDouble(),
            parcel.readString(),
            parcel.readString()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeDouble(latitude)
        dest.writeDouble(longitude)
        dest.writeString(address)
        dest.writeString(name)
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