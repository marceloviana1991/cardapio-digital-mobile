package marceloviana1991.cardapiodigital.http

import marceloviana1991.cardapiodigital.dto.GrupoResponse
import marceloviana1991.cardapiodigital.dto.LoginRequest
import marceloviana1991.cardapiodigital.dto.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    suspend fun login(@Body login: LoginRequest): Response<LoginResponse>

    @GET("grupo")
    suspend fun listarGrupos(@Header("Authorization") token: String): Response<List<GrupoResponse>>
}