package com.dev_camp.auth.service

import com.dev_camp.auth.dto.AccessTokenUpdateRequestDto
import com.dev_camp.auth.dto.AccessTokenUpdateResponseDto
import com.dev_camp.auth.dto.LoginRequestDto
import com.dev_camp.auth.dto.LoginResponseDto
import com.dev_camp.auth.exception.AuthenticateException
import com.dev_camp.auth.exception.LoginException
import com.dev_camp.auth.tools.JwtTokenUtil
import com.dev_camp.user.domain.User
import com.dev_camp.user.domain.UserRepository
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val jwtTokenUtil: JwtTokenUtil,
    private val userRepository: UserRepository,
    private val encoder: BCryptPasswordEncoder,
    private val driver:WebDriver
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

    @Transactional
    fun login(requestDto: LoginRequestDto): LoginResponseDto {
        driver.get("https://smartid.ssu.ac.kr/Symtra_sso/smln.asp?apiReturnUrl=https%3A%2F%2Fsaint.ssu.ac.kr%2FwebSSO%2Fsso.jsp")

        // input id/password in u-saint login page
        driver.findElement(By.id("userid")).clear()
        driver.findElement(By.id("userid")).sendKeys(requestDto.studentId)

        driver.findElement(By.id("pwd")).clear()
        driver.findElement(By.id("pwd")).sendKeys(requestDto.password)
        driver.findElement(By.id("pwd")).submit()
        Thread.sleep(300)

        System.out.print(driver.currentUrl)

        if(driver.currentUrl.contains("https://saint.ssu.ac.kr/irj/portal")){
            val user= User(id=requestDto.studentId,name="testName")
            //로그아웃 버튼 click
            driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/header/div[1]/div/div/button[2]")).click()
            userRepository.save(user)
            driver.quit()

            return LoginResponseDto(
                accessToken = jwtTokenUtil.generateAccessToken(user.id!!),
                refreshToken = jwtTokenUtil.generateAccessToken(user.id!!),
            )

        }
        else {
            driver.quit()
            throw LoginException()
        }

    }
}
