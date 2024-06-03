package com.drg.shlapp.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    //*****************************
    //INTERFAZ de llamadas a la API

    //https://www.superheroapi.com/api.php/122103398894219278/search/super

    //Funcion para hacer una peticion a la API de buscar superheroe por nombre
    @GET("api/122103398894219278/search/{name}")
    suspend fun getSuperheroes(@Path("name") superheroename: String):Response<SuperHeroDataResponse>

    @GET("api/122103398894219278/{character-id}")
    suspend fun getSuperHeroDetail(@Path("character-id") superheroId: String): Response<SuperHeroDetailResponse>
}