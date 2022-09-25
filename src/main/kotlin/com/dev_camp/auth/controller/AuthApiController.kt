package com.dev_camp.auth.controller

import com.dev_camp.auth.dto.AccessTokenUpdateRequestDto
import com.dev_camp.auth.dto.AccessTokenUpdateResponseDto
import com.dev_camp.auth.dto.LoginRequestDto
import com.dev_camp.auth.dto.LoginResponseDto
import com.dev_camp.auth.service.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.Semaphore
import javax.validation.Valid

@RestController
class AuthApiController(
    private val authService: AuthService,
) {

    @PostMapping("/v1/auth/update-token")
    fun updateAccessToken(@Valid @RequestBody requestDto: AccessTokenUpdateRequestDto): AccessTokenUpdateResponseDto {
        return authService.updateAccessToken(requestDto)
    }

    @PostMapping("/v1/auth/login")
    fun login(@Valid @RequestBody requestDto: LoginRequestDto): LoginResponseDto {
        val semaphore = Semaphore(4)
        val response : LoginResponseDto
        semaphore.acquire()
        response = authService.login(requestDto)
        semaphore.release()
        return response
    }
}
