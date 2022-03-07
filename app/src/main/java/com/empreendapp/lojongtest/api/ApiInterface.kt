package com.empreendapp.lojongtest.api

import com.empreendapp.lojongtest.model.Step
import retrofit2.Response

import retrofit2.http.GET
import java.util.concurrent.CompletableFuture

interface ApiInterface {

    @GET(value = "/facts")
    suspend fun getSteps(): Response<List<Step>>

}