package com.dev_camp.reservation.service

import com.dev_camp.reservation.domain.Reservation
import com.dev_camp.user.domain.User

interface ReservationService {
    fun createReservation(terraceId: Int, user: User) : Reservation
}