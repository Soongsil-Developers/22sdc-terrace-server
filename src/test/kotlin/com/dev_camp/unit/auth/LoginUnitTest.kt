package com.dev_camp.unit.auth

import com.dev_camp.auth.dto.LoginRequestDto
import com.dev_camp.auth.exception.LoginException
import com.dev_camp.auth.service.AuthService
import com.dev_camp.auth.tools.JwtTokenUtil
import com.dev_camp.config.WebDriverConfig
import com.dev_camp.unit.BaseUnitTest
import com.dev_camp.user.domain.User
import com.dev_camp.util.*
import com.ninjasquad.springmockk.MockkBean
import com.ninjasquad.springmockk.SpykBean
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.openqa.selenium.WebDriver
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class LoginUnitTest : BaseUnitTest() {

    companion object {
        private const val WRONG_PASSWORD = "wrongpassword"
    }

    var ctx: ApplicationContext = AnnotationConfigApplicationContext(WebDriverConfig::class.java)
    private var driver: WebDriver= ctx.getBean("webDriver",WebDriver::class.java)

    private lateinit var authService: AuthService

    @MockkBean
    private lateinit var encoder: BCryptPasswordEncoder

    @SpykBean
    private lateinit var jwtTokenUtil: JwtTokenUtil

    @BeforeEach
    fun setUp() {
        authService = AuthService(jwtTokenUtil, userRepository, encoder, driver)
    }

    @DisplayName("로그인 성공")
    @Test
    fun login_Success() {
        val user = User(id= USER_ID,name= NAME)
        every { encoder.matches(any(), any()) } returns true
        every { userRepository.save(any()) } returns getMockUser()
        val requestDto = LoginRequestDto(studentId = USER_ID, password =  PASSWORD)
        val responseDto = authService.login(requestDto)
        jwtTokenUtil.isTokenExpired(responseDto.accessToken) shouldBe false
        jwtTokenUtil.isTokenExpired(responseDto.refreshToken) shouldBe false
    }

    @DisplayName("로그인 실패 - 비밀번호 불일치")
    @Test
    fun login_FailIfWrongPassword() {
        every { encoder.matches(any(), any()) } returns false
        every { userRepository.save(any()) } returns getMockUser()
        val requestDto = LoginRequestDto(studentId = USER_ID, password = WRONG_PASSWORD)
        val exception = shouldThrow<LoginException> { authService.login(requestDto) }
        exception.message shouldBe "학번 또는 비밀번호가 잘못되었습니다."
    }

    @DisplayName("로그인 실패 - 존재하지 않는 학번")
    @Test
    fun login_FailIfWrongEmail() {
        every { encoder.matches(any(), any()) } returns false
        every { userRepository.save(any()) } returns getMockUser()
        val requestDto = LoginRequestDto(studentId = "wrongid", password = PASSWORD)
        val exception = shouldThrow<LoginException> { authService.login(requestDto) }
        exception.message shouldBe "학번 또는 비밀번호가 잘못되었습니다."
    }
}
