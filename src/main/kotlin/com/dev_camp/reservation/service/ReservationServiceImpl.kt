package com.dev_camp.reservation.service

import com.dev_camp.auth.exception.InvalidAccessException
import com.dev_camp.auth.exception.UserIdNotFoundException
import com.dev_camp.reservation.domain.Reservation
import com.dev_camp.reservation.domain.ReservationRepository
import com.dev_camp.reservation.dto.ReservationDto
import com.dev_camp.terrace.domain.domain.Terrace
import com.dev_camp.terrace.domain.domain.TerraceRepository
import com.dev_camp.terrace.domain.domain.TerraceStatus
import com.dev_camp.user.domain.User
import com.dev_camp.user.domain.UserRepository
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class ReservationServiceImpl (
    private val reservationRepository: ReservationRepository,
    private val userRepository: UserRepository,
    private val terraceRepository: TerraceRepository
) : ReservationService{
    override fun createReservation(reservationDto: ReservationDto) : Reservation {
        try {
            val terrace: Terrace = terraceRepository.getById(reservationDto.terraceId)
            val user: User = userRepository.getById(reservationDto.userId)

            return when (terrace.status) {
                TerraceStatus.AVAILABLE -> return reservationRepository.save(reservationDto.toEntity(user, terrace))
                TerraceStatus.BOOK -> throw InvalidAccessException("reserved terrace")
                TerraceStatus.CLOSED -> throw InvalidAccessException("not available terrace")
                TerraceStatus.USING -> throw InvalidAccessException("using terrace")
            }
        } catch(e: EntityNotFoundException) {
            throw UserIdNotFoundException()
        }
    }
}