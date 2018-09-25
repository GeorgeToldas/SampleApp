package com.toldas.sampleapplication.data.model

import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class MapLocation(
        @PrimaryKey var id: Long = UUID.randomUUID().mostSignificantBits,
        var latitude: Double = 0.0,
        var longitude: Double = 0.0,
        var address: String = "",
        var label: String = "",
        var distance: Float = 0.0f
) : RealmObject(), Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readDouble(),
            parcel.readDouble(),
            parcel.readString(),
            parcel.readString(),
            parcel.readFloat()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeLong(id)
        dest.writeDouble(latitude)
        dest.writeDouble(longitude)
        dest.writeString(address)
        dest.writeString(label)
        dest.writeFloat(distance)
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