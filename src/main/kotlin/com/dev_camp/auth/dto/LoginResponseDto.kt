package com.dev_camp.auth.dto

data class LoginResponseDto(
    val accessToken: String = "",
    val refreshToken: String = ""
)
