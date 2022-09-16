package com.dev_camp.integration

import com.fasterxml.jackson.databind.ObjectMapper
import com.dev_camp.auth.exception.UserIdNotFoundException
import com.dev_camp.auth.tools.JwtTokenUtil
import com.dev_camp.user.domain.User
import com.dev_camp.user.domain.UserRepository
import com.dev_camp.util.NAME
import com.dev_camp.util.USER_ID
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MockMvcResultMatchersDsl

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
abstract class ApiIntegrationTest {

    @Autowired
    protected lateinit var userRepository: UserRepository

    @Autowired
    protected lateinit var mockMvc: MockMvc

    @Autowired
    protected lateinit var jwtTokenUtil: JwtTokenUtil

    @Autowired
    protected lateinit var encoder: BCryptPasswordEncoder

    @Autowired
    protected lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setUp() {
        val user = User(
            id= USER_ID,
            name = NAME,
        )
        userRepository.save(user)
    }

    @AfterEach
    fun tearDown() {
        userRepository.deleteAll()
    }

    protected fun getUserId(): String {
        val user = userRepository.findById(USER_ID).orElseThrow { UserIdNotFoundException() }
        return user.id ?: "null"
    }

    protected fun assertErrorResponse(dsl: MockMvcResultMatchersDsl, message: String) {
        dsl.jsonPath("timestamp") { exists() }
        dsl.jsonPath("status") { exists() }
        dsl.jsonPath("error") { exists() }
        dsl.jsonPath("message") { value(message) }
        dsl.jsonPath("path") { exists() }
        dsl.jsonPath("remote") { exists() }
    }
}
