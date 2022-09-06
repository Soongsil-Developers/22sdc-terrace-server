package com.dev_camp.auth.exception

import org.springframework.security.core.AuthenticationException

class AuthenticateException(message: String) : AuthenticationException(message)
