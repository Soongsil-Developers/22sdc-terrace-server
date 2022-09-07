package com.dev_camp.auth.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class LoginRequestDto(

    @field:NotBlank(message = "studentId is required.")
    var studentId: String = "",

    @field:NotBlank(message = "Password is required.")
    @field:Size(min = 5, max = 72)
    var password: String = ""
)
