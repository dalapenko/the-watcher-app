package tech.dalapenko.thewatcher.data.model

import com.google.gson.annotations.SerializedName

data class Page<T>(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<T>,
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("total_pages") val totalPages: Int
)
