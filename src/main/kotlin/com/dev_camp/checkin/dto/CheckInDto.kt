package com.dev_camp.checkin.dto

import com.dev_camp.checkin.domain.CheckIn
import com.dev_camp.terrace.domain.Terrace
import com.dev_camp.user.domain.User

class CheckInDto(
    val id: Int?=null,
    val userId: String,
    val terraceId: Int
){
    fun toEntity(user: User, terrace: Terrace): CheckIn {
        return CheckIn(id, user, terrace)
    }
}