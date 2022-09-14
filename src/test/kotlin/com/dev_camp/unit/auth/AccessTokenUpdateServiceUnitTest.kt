package com.dev_camp.unit.auth

import com.ninjasquad.springmockk.MockkBean
import com.dev_camp.auth.dto.AccessTokenUpdateRequestDto
import com.dev_camp.auth.exception.AuthenticateException
import com.dev_camp.auth.service.AuthService
import com.dev_camp.auth.tools.JwtTokenUtil
import com.dev_camp.config.WebDriverConfig
import com.dev_camp.unit.BaseUnitTest
import com.dev_camp.util.TOKEN
import com.dev_camp.util.USER_ID
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.openqa.selenium.WebDriver
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class AccessTokenUpdateServiceUnitTest : BaseUnitTest() {

    private lateinit var authService: AuthService

    var ctx: ApplicationContext = AnnotationConfigApplicationContext(WebDriverConfig::class.java)
    private var driver: WebDriver = ctx.getBean("WebDriver", WebDriver::class.java)

    @MockkBean
    private lateinit var jwtTokenUtil: JwtTokenUtil

    private val encoder = BCryptPasswordEncoder(10)

    @BeforeEach
    fun setUp() {
        every { jwtTokenUtil.generateAccessToken(any()) } returns TOKEN
        every { jwtTokenUtil.generateRefreshToken(any()) } returns TOKEN
        every { jwtTokenUtil.extractUserId(any()) } returns USER_ID
        every { jwtTokenUtil.isTokenExpired(any()) } returns false
        authService = AuthService(jwtTokenUtil, userRepository, encoder,driver)
    }

    @DisplayName("AccessToken 갱신 성공")
    @Test
    fun updateAccessToken_Success() {
        every { userRepository.existsById(any()) } returns true
        val requestDto = AccessTokenUpdateRequestDto(jwtTokenUtil.generateRefreshToken(USER_ID))
        val result = authService.updateAccessToken(requestDto)
        jwtTokenUtil.isTokenExpired(result.accessToken) shouldBe false
    }

    @DisplayName("AccessToken 갱신 실패 - 잘못된 userId인 경우")
    @Test
    fun updateAccessToken_FailWrongUserId() {
        every { userRepository.existsById(any()) } returns false
        val requestDto = AccessTokenUpdateRequestDto(jwtTokenUtil.generateRefreshToken(USER_ID))
        val exception = assertThrows <AuthenticateException> { authService.updateAccessToken(requestDto) }
        exception.message shouldBe "Unauthorized User Id."
    }

    @DisplayName("AccessToken 갱신 실패 - 만료된 refreshToken이 주어진 경우")
    @Test
    fun updateAccessToken_FailIfExpiredRefreshToken() {
        val requestDto = AccessTokenUpdateRequestDto(jwtTokenUtil.generateRefreshToken(USER_ID))
        every { jwtTokenUtil.isTokenExpired(any()) } returns true
        val exception = shouldThrow<AuthenticateException> { authService.updateAccessToken(requestDto) }
        exception.message shouldBe "RefreshToken has been expired."
    }
}
