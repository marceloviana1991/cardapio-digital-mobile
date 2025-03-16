package marceloviana1991.cardapiodigital.dto

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("login") val login: String,
    @SerializedName("senha") val senha: String
)