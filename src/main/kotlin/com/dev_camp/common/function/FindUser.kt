package com.dev_camp.common.function

import com.dev_camp.auth.exception.UserUnAuthorizedException
import com.dev_camp.security.service.UserDetailsImpl
import com.dev_camp.user.domain.User
import com.dev_camp.user.domain.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.function.Supplier

@Component
class FindUser : Supplier<User> {

    @Autowired
    private lateinit var userRepository: UserRepository

    override fun get(): User {
        val userId = Integer.parseInt((SecurityContextHolder.getContext().authentication.principal as UserDetailsImpl).username)
        return userRepository.findById(userId).orElseThrow { UserUnAuthorizedException() }
    }
}
