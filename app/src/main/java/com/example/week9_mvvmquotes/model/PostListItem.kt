package com.example.week9_mvvmquotes.model

import android.os.Parcel
import android.os.Parcelable



data class PostListItem(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt()
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(body)
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeInt(userId)
    }

    companion object CREATOR : Parcelable.Creator<PostListItem> {
        override fun createFromParcel(parcel: Parcel): PostListItem {
            return PostListItem(parcel)
        }

        override fun newArray(size: Int): Array<PostListItem?> {
            return arrayOfNulls(size)
        }
    }
}
