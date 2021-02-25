package com.auto.finder.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/*
* AutoModel consists of the data class based on the JSON response obtained from url
*
*/
@Parcelize
data class Auto(
    var page: Int? = 0,
    var pageSize: Int? = 0,
    var totalPageCount: Int? = 0,
    @SerializedName("wkda")
    @Expose
    val wkda: Map<String, String>? = null
) : Parcelable