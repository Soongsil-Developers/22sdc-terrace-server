package com.dev_camp.reservation.service

import com.dev_camp.reservation.domain.Reservation
import com.dev_camp.reservation.dto.ReservationDto

interface ReservationService {
    fun createReservation(reservationDto: ReservationDto) : Reservation
}