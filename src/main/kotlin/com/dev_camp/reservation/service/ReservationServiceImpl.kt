package com.dev_camp.reservation.service

import com.dev_camp.reservation.domain.Reservation
import com.dev_camp.reservation.domain.ReservationRepository
import com.dev_camp.reservation.dto.ReservationDto
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ReservationServiceImpl (
    private val reservationRepository: ReservationRepository
) : ReservationService{
    fun createReservation(reservationDto: ReservationDto) : Reservation {
        return reservationRepository.save(reservationDto.terraceId, reservationDto.userId, LocalDateTime.now())
    }
}