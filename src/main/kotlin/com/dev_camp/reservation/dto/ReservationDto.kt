package com.dev_camp.reservation.dto

import com.dev_camp.reservation.domain.Reservation
import com.dev_camp.terrace.domain.domain.Terrace
import com.dev_camp.user.domain.User

data class ReservationDto(
    val id: Int? = null,
    val terraceId: Int,
    val userId: String
) {
    fun toEntity(user: User, terrace: Terrace) : Reservation {
        return Reservation(id, terrace, user)
    }
}