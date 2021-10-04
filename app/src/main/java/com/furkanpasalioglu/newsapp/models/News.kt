package com.furkanpasalioglu.newsapp.models


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class News(
    @SerializedName("articles")
    val articles: List<Article>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
) : Serializable