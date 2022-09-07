package com.dev_camp.security.service

import com.dev_camp.common.function.AuthorizeUser
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class JWTUserDetailsService(private val authorizeUser: AuthorizeUser) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        val user = authorizeUser.apply(username!!)
        return UserDetailsImpl(user.id!!)
    }

    fun getAuthorities(): Set<GrantedAuthority> {
        return mutableSetOf()
    }
}
