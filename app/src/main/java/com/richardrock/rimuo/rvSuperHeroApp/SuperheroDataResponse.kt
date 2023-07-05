package com.richardrock.rimuo.rvSuperHeroApp

import com.google.gson.annotations.SerializedName

data class SuperheroDataResponse(
    @SerializedName("response") val response: String,
    @SerializedName("results") val superHeros: List<SuperheroItemsResponse>
)

data class SuperheroItemsResponse(
    @SerializedName("id") val superheroID: String,
    @SerializedName("name") val superheroName: String,
    @SerializedName("image") val superheroImage: SuperheroImageResponse
)

data class SuperheroImageResponse(
    @SerializedName("url") val url: String
)