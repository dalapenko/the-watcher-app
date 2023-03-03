package tech.dalapenko.thewatcher.network.dto

import com.google.gson.annotations.SerializedName

data class Page<T>(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<T>,
    @SerializedName("dates") val dates: Dates? = null,
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("total_pages") val totalPages: Int
)
