package com.dev_camp.reservation.service

import com.dev_camp.reservation.domain.Reservation

interface ReservationService {
    fun createReservation(terraceId: Int, userId: String) : Reservation
}