package com.drg.shlapp.retrofit

import com.google.gson.annotations.SerializedName

data class SuperHeroDetailResponse(
    @SerializedName("name") val name: String,
    @SerializedName("powerstats") val powerstats: SuperHeroPowerStatsResponse,
    @SerializedName("image") val image: superHeroImageDetailResponse,
    @SerializedName("biography") val biography: superHeroBiographyDetailResponse
)

data class SuperHeroPowerStatsResponse(
    @SerializedName("intelligence") val intelligence: String,
    @SerializedName("strength") val strength: String,
    @SerializedName("speed") val speed: String,
    @SerializedName("durability") val durability: String,
    @SerializedName("power") val power: String,
    @SerializedName("combat") val combat: String
)

data class superHeroImageDetailResponse(@SerializedName("url") var url: String)

data class superHeroBiographyDetailResponse(
    @SerializedName("full-name") var fullName: String,
    @SerializedName("publisher") var publisher: String
)