package com.example.mycinemaapp.model.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class MovieEntityRoomDto(
    @PrimaryKey(autoGenerate = true) val roomId:Int,

    @ColumnInfo @SerializedName("character") val character: String,
    @ColumnInfo @SerializedName("id") val id: Int,
): Parcelable
