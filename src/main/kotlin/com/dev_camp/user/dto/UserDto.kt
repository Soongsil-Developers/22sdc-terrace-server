package com.dev_camp.user.dto

import com.dev_camp.user.domain.User

data class UserDto(
    val id: String,
    val name: String,

) {
    constructor(user: User) : this(user.id!!, user.name)

    fun toResponseDto() = UserInfoResponseDto(this.id, this.name)
}
