package com.onix.demographql.data.pojo

import android.os.Parcel
import android.os.Parcelable

class ChildInfo(var starsCount: Int, var openIssuesCount: Int, var url: String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString()) {
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(starsCount)
        dest.writeInt(openIssuesCount)
        dest.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ChildInfo> {
        override fun createFromParcel(parcel: Parcel): ChildInfo {
            return ChildInfo(parcel)
        }

        override fun newArray(size: Int): Array<ChildInfo?> {
            return arrayOfNulls(size)
        }
    }
}