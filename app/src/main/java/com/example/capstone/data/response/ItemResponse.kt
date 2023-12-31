package com.example.capstone.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemResponse (
    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("photoUrl")
    val photoUrl: String? = null,

    @field:SerializedName("batik_name")
    val batikName: String? = null,

    @field:SerializedName("price")
    val price: Int? = null,

    @field:SerializedName("technique")
    val technique: String? = null,

    @field:SerializedName("content")
    val content: String? = null,

    @field:SerializedName("asal_daerah")
    val asalDaerah: String? = null
) : Parcelable