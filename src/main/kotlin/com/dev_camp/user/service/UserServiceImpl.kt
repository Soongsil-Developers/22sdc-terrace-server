package com.dev_camp.user.service

import com.dev_camp.user.domain.User
import com.dev_camp.user.domain.UserRepository
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    val userRepository: UserRepository
) : UserService{
    override fun isUserHere(): User {
        return userRepository.findById("ASDF").orElseThrow { RuntimeException("A") }
    }
}