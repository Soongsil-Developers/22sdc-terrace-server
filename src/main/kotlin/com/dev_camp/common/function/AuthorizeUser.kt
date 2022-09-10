package com.dev_camp.common.function

import com.dev_camp.user.domain.User
import com.dev_camp.user.domain.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import java.util.function.Function

@Component
class AuthorizeUser : Function<String, User> {

    @Autowired
    private lateinit var userRepository: UserRepository

    override fun apply(userId: String): User {
        return userRepository.findById(userId).orElseThrow { UsernameNotFoundException("잘못된 userId 값입니다.") }
    }
}
