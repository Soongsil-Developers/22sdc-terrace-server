package com.dev_camp.auth.service

import com.dev_camp.auth.dto.AccessTokenUpdateRequestDto
import com.dev_camp.auth.dto.AccessTokenUpdateResponseDto
import com.dev_camp.auth.dto.LoginRequestDto
import com.dev_camp.auth.dto.LoginResponseDto
import com.dev_camp.auth.exception.AuthenticateException
import com.dev_camp.auth.exception.LoginException
import com.dev_camp.auth.tools.JwtTokenUtil
import com.dev_camp.user.domain.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val jwtTokenUtil: JwtTokenUtil,
    private val userRepository: UserRepository,
    private val encoder: BCryptPasswordEncoder
) {

    @Transactional(readOnly = true)
    fun updateAccessToken(dto: AccessTokenUpdateRequestDto): AccessTokenUpdateResponseDto {
        if (!jwtTokenUtil.isTokenExpired(dto.refreshToken)) {
            val userId = jwtTokenUtil.extractUserId(dto.refreshToken)
            if (userRepository.existsById(userId)) {
                return AccessTokenUpdateResponseDto(jwtTokenUtil.generateAccessToken(userId))
            } else throw AuthenticateException("Unauthorized User Id.")
        } else throw AuthenticateException("RefreshToken has been expired.")
    }

    @Transactional(readOnly = true)
    fun login(requestDto: LoginRequestDto): LoginResponseDto {
        val user = userRepository.findById(requestDto.studentId).orElseThrow { LoginException() }
        //if (!encoder.matches(requestDto.password, user.password)) throw LoginException()
        //추후 login request를 유세인트로 날려서 확인할 것으로 예상
        return LoginResponseDto(
            accessToken = jwtTokenUtil.generateAccessToken(user.id!!),
            refreshToken = jwtTokenUtil.generateAccessToken(user.id!!),
        )
    }
}
