package com.dev_camp.reservation.service

import com.dev_camp.auth.exception.UserIdNotFoundException
import com.dev_camp.reservation.domain.Reservation
import com.dev_camp.reservation.domain.ReservationRepository
import com.dev_camp.terrace.domain.Terrace
import com.dev_camp.terrace.domain.TerraceRepository
import com.dev_camp.terrace.domain.TerraceStatus
import com.dev_camp.terrace.exception.TerraceIdNotFoundException
import com.dev_camp.terrace.exception.UnavailableTerraceException
import com.dev_camp.user.domain.User
import com.dev_camp.user.domain.UserRepository
import org.springframework.stereotype.Service

@Service
class ReservationServiceImpl (
    private val reservationRepository: ReservationRepository,
    private val userRepository: UserRepository,
    private val terraceRepository: TerraceRepository,
) : ReservationService {
    override fun createReservation(terraceId: Int, userId: String) : Reservation {
        val terrace: Terrace = terraceRepository.findById(terraceId).orElseThrow { TerraceIdNotFoundException() }
        val user: User = userRepository.findById(userId).orElseThrow { UserIdNotFoundException() }

        return when (terrace.status) {
            TerraceStatus.AVAILABLE -> return reservationRepository.save(Reservation(null, terrace, user))
            TerraceStatus.BOOK -> throw UnavailableTerraceException("이미 예약된 테라스입니다.")
            TerraceStatus.CLOSED -> throw UnavailableTerraceException("사용 불가능한 테라스입니다.")
            TerraceStatus.USING -> throw UnavailableTerraceException("사용 중인 테라스입니다.")
        }
    }
}