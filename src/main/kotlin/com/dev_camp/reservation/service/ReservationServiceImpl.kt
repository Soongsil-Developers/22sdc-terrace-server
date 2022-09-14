package com.dev_camp.reservation.service

import com.dev_camp.common.exception.ApiException
import com.dev_camp.common.exception.NotFoundException
import com.dev_camp.reservation.domain.Reservation
import com.dev_camp.reservation.domain.ReservationRepository
import com.dev_camp.reservation.dto.ReservationDto
import com.dev_camp.terrace.domain.Terrace
import com.dev_camp.terrace.domain.TerraceRepository
import com.dev_camp.terrace.domain.TerraceStatus
import com.dev_camp.user.domain.User
import com.dev_camp.user.domain.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.persistence.EntityNotFoundException

@Service
class ReservationServiceImpl (
    private val reservationRepository: ReservationRepository,
    private val userRepository: UserRepository,
    private val terraceRepository: TerraceRepository
) : ReservationService{
    override fun createReservation(reservationDto: ReservationDto) : Reservation {
        val terrace: Terrace = terraceRepository.getById(reservationDto.terraceId)
        val user: User = userRepository.getById(reservationDto.userId)

        return when (terrace.status) {
            TerraceStatus.AVAILABLE -> return reservationRepository.save(reservationDto.toEntity(user, terrace))
            TerraceStatus.BOOK -> throw ApiException()
            TerraceStatus.CLOSED -> throw ApiException()
            TerraceStatus.USING -> throw ApiException()
        }
    }
}