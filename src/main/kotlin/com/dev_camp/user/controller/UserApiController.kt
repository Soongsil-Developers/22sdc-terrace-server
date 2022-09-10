package com.dev_camp.user.controller

import com.dev_camp.user.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/user/")
class UserApiController(
    private val userService: UserService
) {

}
