package com.richardrock.rimuo.rvSuperHeroApp

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    //1440091633475613
    @GET("/api/1440091633475613/search/{name}")
    suspend fun getSuperheroes(@Path("name") superheroName: String): Response<SuperheroDataResponse>

    //https://superheroapi.com/api/access-token/character-id
    @GET("/api/1440091633475613/{id}/")
    suspend fun getSuperheroesDetailID(@Path("id") superheroID: String):Response<SuperheroDetailResponse>
}