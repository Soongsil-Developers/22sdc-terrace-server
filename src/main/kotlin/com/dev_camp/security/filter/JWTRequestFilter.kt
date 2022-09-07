package com.dev_camp.security.filter

import com.dev_camp.auth.exception.AuthenticateException
import com.dev_camp.auth.tools.JwtTokenUtil
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTRequestFilter(
    private val jwtTokenUtil: JwtTokenUtil,
    private val authenticationEntryPoint: AuthenticationEntryPoint
) : OncePerRequestFilter() {

    companion object {
        private const val BEARER_SCHEME = "Bearer"
        private const val AUTHORIZATION_HEADER = "Authorization"
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val authorizationHeader = request.getHeader(AUTHORIZATION_HEADER)
                ?: throw AuthenticateException("Authorization Header is missing.")
            val token = extractAccessToken(authorizationHeader)
            val authentication = jwtTokenUtil.verify(token)
            val context = SecurityContextHolder.getContext()
            context.authentication = authentication
            filterChain.doFilter(request, response)
        } catch (exception: AuthenticateException) {
            authenticationEntryPoint.commence(request, response, exception)
        }
    }

    private fun validateAuthorizationHeader(splits: List<String>) {
        if (splits.size != 2) throw AuthenticateException("Authorization Header is malformed.")
        val scheme = splits[0]
        if (scheme != BEARER_SCHEME) throw AuthenticateException("Scheme is not Bearer.")
    }

    private fun extractAccessToken(authorizationHeader: String): String {
        val splits = authorizationHeader.split(" ")
        validateAuthorizationHeader(splits)
        return splits[1]
    }
}
